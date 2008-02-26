package com.binaryorder.embeddedpc.emulator.peripheral;

import junit.framework.TestCase;

public class ProgrammablePeripheralInterfaceTest extends TestCase {
	public void testPortA() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		assertEquals(0x3C, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_A));
	}

	public void testPortC() {
		ProgrammablePeripheralInterface ppi = new ProgrammablePeripheralInterface(512 * 1024);
		assertEquals(0x03, ppi.ioPortReadByte(ProgrammablePeripheralInterface.PORT_C));
	}
}
