package com.binaryorder.embeddedpc.emulator.peripheral;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import junit.framework.TestCase;

import org.jpc.emulator.HardwareComponent;
import org.jpc.emulator.motherboard.InterruptController;
import org.jpc.emulator.motherboard.IntervalTimer;

public class ProgrammablePeripheralInterfaceTest extends TestCase {
	public void testSetMode() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		ppi.ioPortWriteByte(ProgrammablePeripheralInterface.CMD_PORT, 0x99);
	}

	public void testPortA() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		assertEquals(0x3C, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_A));
	}

	public void testPortC() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		assertEquals(0x03, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_C));
	}

	public void testReadKeyboard() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		ppi.acceptComponent(new IntervalTimer() {
			public void setGate(int channel, boolean value) {
			}
		});
		ppi.acceptComponent(new InterruptController() {
			public int cpuGetInterrupt() {
				return 0;
			}

			public void dumpState(DataOutput output) throws IOException {
			}

			public void loadState(DataInput input) throws IOException {
			}

			public void setIRQ(int irqNumber, int level) {
			}

			public int ioPortReadByte(int address) {
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

			@Override
			public void ioPortWriteByte(int address, int data) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ioPortWriteLong(int address, int data) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ioPortWriteWord(int address, int data) {
				// TODO Auto-generated method stub

			}

			@Override
			public int[] ioPortsRequested() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void acceptComponent(HardwareComponent component) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean initialised() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

			@Override
			public void timerCallback() {
				// TODO Auto-generated method stub

			}

			@Override
			public void updateComponent(HardwareComponent component) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean updated() {
				// TODO Auto-generated method stub
				return false;
			}

		});
		ppi.ioPortWriteByte(ProgrammablePeripheralInterface.PORT_B, 0x4C);
		assertEquals((byte) 0xAA, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_A));
		ppi.ioPortWriteByte(ProgrammablePeripheralInterface.PORT_B, 0x0C);
		ppi.keyPressed((byte) 0x12);
		assertEquals(0x12, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_A));
	}
}
