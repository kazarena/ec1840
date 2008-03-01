package com.binaryorder.embeddedpc.emulator.peripheral;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Logger;

import org.jpc.emulator.AbstractHardwareComponent;
import org.jpc.emulator.HardwareComponent;
import org.jpc.emulator.motherboard.IOPortCapable;
import org.jpc.emulator.motherboard.IOPortHandler;
import org.jpc.emulator.motherboard.InterruptController;
import org.jpc.emulator.motherboard.IntervalTimer;
import org.jpc.emulator.peripheral.UserInputDevice;

/**
 * 8255 Programmable Peripheral Interface (PPI)
 * 
 * <pre>
 * Port  Description
 *  ฿฿฿฿  ฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿฿
 *  060H  þPC/XTþ  PPI port A.  Read keyboard scan code:
 *  IN   al,60H  ;fetches most recent scan code.
 * 
 *  061H  þPC/XTþ PPI (Programmable Peripheral Interface) port B.
 *  ึ7ย6ย5ย4ย3ย2ย1ย0ท
 *  บ ณ ณ ณ ณ ณ0ณ ณ บ
 *  ำามามามามามฤมามาฝ bit
 *  บ บ บ บ บ   บ ศอ 0: Timer 2 gate (speaker)  อหอ OR 03H=speaker ON
 *  บ บ บ บ บ   ศอออ 1: Timer 2 data  อออออออออออผ  AND 0fcH=speaker OFF
 *  บ บ บ บ ศอออออออ 3: 1=read high switches; 0=read low switches(see 62H)
 *  บ บ บ ศอออออออออ 4: 0=enable RAM parity checking; 1=disable
 *  บ บ ศอออออออออออ 5: 0=enable I/O channel check
 *  บ ศอออออออออออออ 6: 0=hold keyboard clock low
 *  ศอออออออออออออออ 7: 0=enable keyboard; 1=disable keyboard
 * 
 *  062H  þPC/XTþ PPI port C.
 *  ึ7ย6ย5ย4ย3ย2ย1ย0ท
 *  บ ณ ณ ณ0ณequip'tบ
 *  ำามามามฤมฤมฤมฤมฤฝ bit
 *  บ บ บ   ศอออออสอ 0-3: values of DIP switches.  See Equipment List
 *  บ บ ศอออออออออออ 5: 1=Timer 2 channel out
 *  บ ศอออออออออออออ 6: 1=I/O channel check
 *  ศอออออออออออออออ 7: 1=RAM parity check error occurred.
 * 
 *  063H  þPC/XTþ PPI Command/Mode Register.  Selects which PPI ports are input
 *  or output.  BIOS sets to 99H (Ports A and C are input, B is output).
 * 
 * </pre>
 */
public class ProgrammablePeripheralInterface extends AbstractHardwareComponent implements IOPortCapable,
		HardwareComponent, UserInputDevice {
	protected Logger logger = Logger.getLogger("PPI8255");

	public static final byte KBD_CLEAR = (byte) 0x80;
	public static final byte KBD_CLK_HIGH = (byte) 0x40;

	public static final int PORT_A = 0x60;
	/**
	 * <pre>
	 * 0061	w	PPI  Programmable Peripheral Interface 8255 (XT only)
	 * 		system control port
	 * 		 bit 7 = 1  clear keyboard
	 * 		 bit 6 = 0  hold keyboard clock low
	 * 		 bit 5 = 0  I/O check enable
	 * 		 bit 4 = 0  RAM parity check enable
	 * 		 bit 3 = 0  read low switches
	 * 		 bit 2	    reserved, often used as turbo switch
	 * 		 bit 1 = 1  speaker data enable
	 * 		 bit 0 = 1  timer 2 gate to speaker enable
	 * </pre>
	 */
	public static final int PORT_B = 0x61;
	/**
	 * <pre>
	 * 	0062	r/w	PPI (XT only)
	 * 		 bit 7 = 1  RAM parity check
	 * 		 bit 6 = 1  I/O channel check
	 * 		 bit 5 = 1  timer 2 channel out
	 * 		 bit 4	    reserved 
	 * 		 bit 3 = 1  system board RAM size type 1
	 * 		 bit 2 = 1  system board RAM size type 2
	 * 		 bit 1 = 1  coprocessor installed
	 * 		 bit 0 = 1  loop in POST
	 * </pre>
	 */
	public static final int PORT_C = 0x62;
	/**
	 * <pre>
	 * 	0063	r/w	PPI (XT only) command mode register  (read dipswitches)
	 * 		 bit 7-6 = 00  1 diskette drive
	 * 			 = 01  2 diskette drives
	 * 			 = 10  3 diskette drives
	 * 			 = 11  4 diskette drives
	 * 		 bit 5-4 = 00  reserved
	 * 			 = 01  40*25 color (mono mode)
	 * 			 = 10  80*25 color (mono mode)
	 * 			 = 11  MDA 80*25
	 * 		 bit 3-2 = 00  256K (using 256K chips)
	 * 			 = 01  512K (using 256K chips)
	 * 			 = 10  576K (using 256K chips)
	 * 			 = 11  640K (using 256K chips)
	 * 		 bit 3-2 = 00  64K  (using 64K chips)
	 * 			 = 01  128K (using 64K chips)
	 * 			 = 10  192K (using 64K chips)
	 * 			 = 11  256K (using 64K chips)
	 * 		 bit 1-0       reserved
	 * </pre>
	 */
	public static final int CMD_PORT = 0x63;

	private byte[] portA = new byte[] { 0x20 | 0x0C, 0 };
	private byte portB = KBD_CLEAR;
	private byte[] portC = new byte[] { 0, 0 };

	private byte mode = (byte) 0x80;
	private byte group_a_mode = 0;
	private byte group_b_mode = 0;
	private boolean portA_in = false;
	private boolean portB_in = false;
	private boolean portC_low_in = false;
	private boolean portC_high_in = false;

	private boolean ioportRegistered = false;

	private IntervalTimer pit = null;
	private InterruptController irqDevice = null;

	private KeyboardQueue queue;

	public ProgrammablePeripheralInterface(int memorySize) {
		memorySize /= 128 * 1024;

		queue = new KeyboardQueue();

		// mem size for the pc = (portC+1)*128 (e.g. 3 - 512 Kb)
		portC[1] = memorySize >= 0 ? (byte) (memorySize - 1) : 0;
	}

	public int ioPortReadByte(int address) {
		int result = privateReadByte(address);
		// System.out.println("Read " + Integer.toHexString(result) + " from " +
		// Integer.toHexString(address));
		return result;
	}

	private int privateReadByte(int address) {
		switch(address) {
		case PORT_A:

			// if (pc->ppi_port_b & 0x80) {
			// return (pc->ppi_port_a[0]);
			// }
			// else {
			// return (pc->ppi_port_a[1]);
			// }
			if(0 != (portB & KBD_CLEAR)) {
				return portA[0];
			} else {
				portA[1] = queue.readData();
				return portA[1];
			}
		case PORT_B:
			return portB;
		case PORT_C:

			// if (pc->ppi_port_b & 0x04) {
			// return (pc->ppi_port_c[1]);
			// }
			// else {
			// return (pc->ppi_port_c[0]);
			// }
			if(0 == (portB & 0x04)) {
				return portC[0];
			} else {
				return portC[1];
			}
		case CMD_PORT:
			return mode;
		}
		// System.out.println("PPI called " + address);
		return 0;
	}

	@Override
	public int ioPortReadLong(int address) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ioPortReadWord(int address) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void ioPortWriteByte(int address, int data) {
		switch(address) {
		case PORT_B:
			// pc->ppi_port_b = val;
			//
			// e8253_set_gate (&pc->pit, 2, val & 0x01);
			portB = (byte) (data & 0xff);
			pit.setGate(2, (data & 1) != 0);

			if((data & KBD_CLEAR) != 0) {
				System.out.println("Received CLEAR_KBD");
				queue.reset();
				// queue.writeData((byte) 0xAA, (byte) 0);
			}
			if((data & KBD_CLK_HIGH) != 0) {
				System.out.println("Received KBD_CLK_HIGH");
				// queue.reset();
				queue.writeData((byte) 0xAA, (byte) 0);
			}
			break;
		case CMD_PORT:
			byte val = (byte) data;
			if((val & 0x80) != 0) {
				// change mode
				mode = val;

				group_a_mode = (byte) ((val >> 5) & 0x03);
				group_b_mode = (byte) ((val >> 2) & 0x01);
				portA_in = (val & 0x10) != 0;
				portB_in = (val & 0x02) != 0;
				portC_low_in = (val & 0x01) != 0;
				portC_high_in = (val & 0x08) != 0;

				System.out.println("8255 mode change: A=" + (portA_in ? "in" : "out") + " B="
						+ (portB_in ? "in" : "out") + " Cl=" + (portC_low_in ? "in" : "out") + " Ch="
						+ (portC_high_in ? "in" : "out"));
			} else {
				System.out.println("set bits invoked");
				// set bits in port C
				byte bit;
				// get the bit number
				bit = (byte) ((val >> 1) & 0x07);
				// set/unset the bit
				if((val & 1) != 0) {
					portC[0] = (byte) (portC[0] | (1 << bit));
				} else {
					portC[0] = (byte) (portC[0] & ~(1 << bit));
				}
			}

		}
	}

	@Override
	public void ioPortWriteLong(int address, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ioPortWriteWord(int address, int data) {
		// TODO Auto-generated method stub

	}

	public int[] ioPortsRequested() {
		return new int[] { PORT_A, PORT_B, PORT_C, CMD_PORT };
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void timerCallback() {
		// TODO Auto-generated method stub

	}

	public void acceptComponent(HardwareComponent component) {
		if(component instanceof IOPortHandler) {
			((IOPortHandler) component).registerIOPortCapable(this);
			ioportRegistered = true;
		} else if(component instanceof IntervalTimer) {
			pit = (IntervalTimer) component;
		} else if((component instanceof InterruptController) && component.initialised())
			irqDevice = (InterruptController) component;
	}

	public boolean initialised() {
		return ioportRegistered && pit != null && irqDevice != null;
	}

	public void updateComponent(HardwareComponent component) {
		if(component instanceof IOPortHandler) {
			((IOPortHandler) component).registerIOPortCapable(this);
			ioportRegistered = true;
		} else if(component instanceof IntervalTimer) {
			pit = (IntervalTimer) component;
		}
	}

	public boolean updated() {
		return ioportRegistered && irqDevice.updated() && pit.updated();
	}

	@Override
	public void dumpState(DataOutput output) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadState(DataInput input) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(byte scancode) {
		switch(scancode) {
		case (byte) 0xff:
			putKeyboardEvent((byte) 0xe1);
			putKeyboardEvent((byte) 0x1d);
			putKeyboardEvent((byte) 0x45);
			putKeyboardEvent((byte) 0xe1);
			putKeyboardEvent((byte) 0x9d);
			putKeyboardEvent((byte) 0xc5);
			return;
		default:
			if(scancode < 0)
				putKeyboardEvent((byte) 0xe0);
			putKeyboardEvent((byte) (scancode & 0x7f));
			return;
		}

	}

	void putKeyboardEvent(byte keycode) {
		// pc->ppi_port_a[1] = pc->key_buf[pc->key_i];
		// e8259_set_irq1 (&pc->pic, 1);
		queue.writeData(keycode, (byte) 0);
	}

	public void keyReleased(byte scancode) {
		if(scancode < 0)
			putKeyboardEvent((byte) 0xe0);
		putKeyboardEvent((byte) (scancode | 0x80));
	}

	public void putMouseEvent(int dx, int dy, int dz, int buttons) {
	}

	private static final int KBD_QUEUE_SIZE = 256;

	private class KeyboardQueue {
		private byte[] aux;
		private byte[] data;
		private int readPosition;
		private int writePosition;
		private int length;

		public KeyboardQueue() {
			aux = new byte[KBD_QUEUE_SIZE];
			data = new byte[KBD_QUEUE_SIZE];
			readPosition = 0;
			writePosition = 0;
			length = 0;
		}

		public void dumpState(DataOutput output) throws IOException {
			output.writeInt(aux.length);
			output.write(aux);
			output.writeInt(data.length);
			output.write(data);
			output.writeInt(readPosition);
			output.writeInt(writePosition);
			output.writeInt(length);
		}

		public void loadState(DataInput input) throws IOException {
			int len = input.readInt();
			aux = new byte[len];
			input.readFully(aux, 0, len);
			len = input.readInt();
			data = new byte[len];
			input.readFully(data, 0, len);
			readPosition = input.readInt();
			writePosition = input.readInt();
			length = input.readInt();
		}

		public void reset() {
			readPosition = 0;
			writePosition = 0;
			length = 0;
		}

		public byte getAux() {
			return aux[readPosition];
		}

		public byte readData() {
			if(length == 0) {
				/*
				 * NOTE: if no data left, we return the last keyboard one
				 * (needed for EMM386)
				 */
				/* XXX: need a timer to do things correctly */
				int index = readPosition - 1;
				if(index < 0)
					index = KBD_QUEUE_SIZE - 1;
				return data[index];
			}
			byte aux = this.aux[readPosition];
			byte data = this.data[readPosition];
			if((++readPosition) == KBD_QUEUE_SIZE)
				readPosition = 0;
			length--;
			/* reading deasserts IRQ */
			if(0 != aux) {
				ProgrammablePeripheralInterface.this.irqDevice.setIRQ(12, 0);
			} else {
				ProgrammablePeripheralInterface.this.irqDevice.setIRQ(1, 0);
			}
			return data;
		}

		public void writeData(byte data, byte aux) {
			if(length >= KBD_QUEUE_SIZE)
				return;
			this.aux[writePosition] = aux;
			this.data[writePosition] = data;
			if((++writePosition) == KBD_QUEUE_SIZE)
				writePosition = 0;
			length++;
			ProgrammablePeripheralInterface.this.updateIRQ();
		}
	}

	private synchronized void updateIRQ() {
		int irq1Level = 0;
		int irq12Level = 0;
		// status = (byte) (status & ~(KBD_STAT_OBF | KBD_STAT_MOUSE_OBF));
		if(queue.length != 0) {
			// status = (byte) (status | KBD_STAT_OBF);
			if(0 != queue.getAux()) {
				// status = (byte) (status | KBD_STAT_MOUSE_OBF);
				// if(0 != (mode & KBD_MODE_MOUSE_INT))
				// irq12Level = 1;
			} else {
				// != breaks at "many beeps"
				if(0 != (portB & KBD_CLK_HIGH)) {
					// irq1Level = 1;
				}
			}
		}
		irqDevice.setIRQ(1, irq1Level);
		irqDevice.setIRQ(12, irq12Level);
	}
}
