package com.binaryorder.embeddedpc.pc;

import java.awt.event.KeyEvent;
import java.util.Hashtable;

import org.jpc.j2se.KeyMapping;

public class EC1840KeyMapping implements KeyMapping {
	private Hashtable scancodeTable = new Hashtable();

	public EC1840KeyMapping() {
		scancodeTable.put(new Integer(KeyEvent.VK_ESCAPE), new Byte((byte) 0x01));
		scancodeTable.put(new Integer(KeyEvent.VK_1), new Byte((byte) 0x02));
		scancodeTable.put(new Integer(KeyEvent.VK_2), new Byte((byte) 0x03));
		scancodeTable.put(new Integer(KeyEvent.VK_3), new Byte((byte) 0x04));
		scancodeTable.put(new Integer(KeyEvent.VK_4), new Byte((byte) 0x05));
		scancodeTable.put(new Integer(KeyEvent.VK_5), new Byte((byte) 0x06));
		scancodeTable.put(new Integer(KeyEvent.VK_6), new Byte((byte) 0x07));
		scancodeTable.put(new Integer(KeyEvent.VK_7), new Byte((byte) 0x08));
		scancodeTable.put(new Integer(KeyEvent.VK_8), new Byte((byte) 0x09));
		scancodeTable.put(new Integer(KeyEvent.VK_9), new Byte((byte) 0x0a));
		scancodeTable.put(new Integer(KeyEvent.VK_0), new Byte((byte) 0x0b));
		scancodeTable.put(new Integer(KeyEvent.VK_MINUS), new Byte((byte) 0x0c));
		scancodeTable.put(new Integer(KeyEvent.VK_EQUALS), new Byte((byte) 0x0d));
		scancodeTable.put(new Integer(KeyEvent.VK_BACK_SPACE), new Byte((byte) 0x0e));

		scancodeTable.put(new Integer(KeyEvent.VK_TAB), new Byte((byte) 0xf));
		scancodeTable.put(new Integer(KeyEvent.VK_Q), new Byte((byte) 0x10));
		scancodeTable.put(new Integer(KeyEvent.VK_W), new Byte((byte) 0x11));
		scancodeTable.put(new Integer(KeyEvent.VK_E), new Byte((byte) 0x12));
		scancodeTable.put(new Integer(KeyEvent.VK_R), new Byte((byte) 0x13));
		scancodeTable.put(new Integer(KeyEvent.VK_T), new Byte((byte) 0x14));
		scancodeTable.put(new Integer(KeyEvent.VK_Y), new Byte((byte) 0x15));
		scancodeTable.put(new Integer(KeyEvent.VK_U), new Byte((byte) 0x16));
		scancodeTable.put(new Integer(KeyEvent.VK_I), new Byte((byte) 0x17));
		scancodeTable.put(new Integer(KeyEvent.VK_O), new Byte((byte) 0x18));
		scancodeTable.put(new Integer(KeyEvent.VK_P), new Byte((byte) 0x19));
		scancodeTable.put(new Integer(KeyEvent.VK_OPEN_BRACKET), new Byte((byte) 0x1a));
		scancodeTable.put(new Integer(KeyEvent.VK_CLOSE_BRACKET), new Byte((byte) 0x1b));

		scancodeTable.put(new Integer(KeyEvent.VK_ENTER), new Byte((byte) 0x1c));

		scancodeTable.put(new Integer(KeyEvent.VK_CONTROL), new Byte((byte) 0x1d));

		scancodeTable.put(new Integer(KeyEvent.VK_A), new Byte((byte) 0x1e));
		scancodeTable.put(new Integer(KeyEvent.VK_S), new Byte((byte) 0x1f));
		scancodeTable.put(new Integer(KeyEvent.VK_D), new Byte((byte) 0x20));
		scancodeTable.put(new Integer(KeyEvent.VK_F), new Byte((byte) 0x21));
		scancodeTable.put(new Integer(KeyEvent.VK_G), new Byte((byte) 0x22));
		scancodeTable.put(new Integer(KeyEvent.VK_H), new Byte((byte) 0x23));
		scancodeTable.put(new Integer(KeyEvent.VK_J), new Byte((byte) 0x24));
		scancodeTable.put(new Integer(KeyEvent.VK_K), new Byte((byte) 0x25));
		scancodeTable.put(new Integer(KeyEvent.VK_L), new Byte((byte) 0x26));

		scancodeTable.put(new Integer(KeyEvent.VK_SEMICOLON), new Byte((byte) 0x27));
		scancodeTable.put(new Integer(KeyEvent.VK_QUOTE), new Byte((byte) 0x28));

		scancodeTable.put(new Integer(KeyEvent.VK_BACK_QUOTE), new Byte((byte) 0x29));

		scancodeTable.put(new Integer(KeyEvent.VK_SHIFT), new Byte((byte) 0x54));

		scancodeTable.put(new Integer(KeyEvent.VK_BACK_SLASH), new Byte((byte) 0x2b));

		scancodeTable.put(new Integer(KeyEvent.VK_Z), new Byte((byte) 0x2c));
		scancodeTable.put(new Integer(KeyEvent.VK_X), new Byte((byte) 0x2d));
		scancodeTable.put(new Integer(KeyEvent.VK_C), new Byte((byte) 0x2e));
		scancodeTable.put(new Integer(KeyEvent.VK_V), new Byte((byte) 0x2f));
		scancodeTable.put(new Integer(KeyEvent.VK_B), new Byte((byte) 0x30));
		scancodeTable.put(new Integer(KeyEvent.VK_N), new Byte((byte) 0x31));
		scancodeTable.put(new Integer(KeyEvent.VK_M), new Byte((byte) 0x32));
		scancodeTable.put(new Integer(KeyEvent.VK_COMMA), new Byte((byte) 0x33));
		scancodeTable.put(new Integer(KeyEvent.VK_PERIOD), new Byte((byte) 0x34));
		scancodeTable.put(new Integer(KeyEvent.VK_SLASH), new Byte((byte) 0x35));
		scancodeTable.put(new Integer(KeyEvent.VK_SHIFT), new Byte((byte) 0x54));

		// 37 KPad *

		// 38 Missing L-Alt - Java does not pickup
		scancodeTable.put(new Integer(KeyEvent.VK_ALT), new Byte((byte) 0x38));
		scancodeTable.put(new Integer(KeyEvent.VK_SPACE), new Byte((byte) 0x39));

		scancodeTable.put(new Integer(KeyEvent.VK_CAPS_LOCK), new Byte((byte) 0x56));

		scancodeTable.put(new Integer(KeyEvent.VK_F1), new Byte((byte) 0x3b));
		scancodeTable.put(new Integer(KeyEvent.VK_F2), new Byte((byte) 0x3c));
		scancodeTable.put(new Integer(KeyEvent.VK_F3), new Byte((byte) 0x3d));
		scancodeTable.put(new Integer(KeyEvent.VK_F4), new Byte((byte) 0x3e));
		scancodeTable.put(new Integer(KeyEvent.VK_F5), new Byte((byte) 0x3f));
		scancodeTable.put(new Integer(KeyEvent.VK_F6), new Byte((byte) 0x40));
		scancodeTable.put(new Integer(KeyEvent.VK_F7), new Byte((byte) 0x41));
		scancodeTable.put(new Integer(KeyEvent.VK_F8), new Byte((byte) 0x42));
		scancodeTable.put(new Integer(KeyEvent.VK_F9), new Byte((byte) 0x43));
		scancodeTable.put(new Integer(KeyEvent.VK_F10), new Byte((byte) 0x44));

		// 45 Missing Num-Lock - Java does not pickup

		scancodeTable.put(new Integer(KeyEvent.VK_SCROLL_LOCK), new Byte((byte) 0x46));

		// 47-53 are Numpad keys

		// 54-56 are not used

		scancodeTable.put(new Integer(122), new Byte((byte) 0x57)); // F11
		scancodeTable.put(new Integer(123), new Byte((byte) 0x5b)); // F12

		// 59-ff are unused (for normal keys)

		// Extended Keys
		// e0,1c KPad Enter
		// e0,1d R-Ctrl
		// e0,2a fake L-Shift
		// e0,35 KPad /
		// e0,36 fake R-Shift
		// e0,37 Ctrl + Print Screen
		scancodeTable.put(new Integer(KeyEvent.VK_ALT_GRAPH), new Byte((byte) (0x38 | 0x80)));
		// e0,46 Ctrl + Break
		scancodeTable.put(new Integer(KeyEvent.VK_HOME), new Byte((byte) (0x47 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_UP), new Byte((byte) (0x48 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_PAGE_UP), new Byte((byte) (0x49 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_LEFT), new Byte((byte) (0x4b | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_RIGHT), new Byte((byte) (0x4d | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_END), new Byte((byte) (0x4f | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_DOWN), new Byte((byte) (0x50 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_PAGE_DOWN), new Byte((byte) (0x51 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_INSERT), new Byte((byte) (0x52 | 0x80)));
		scancodeTable.put(new Integer(KeyEvent.VK_DELETE), new Byte((byte) (0x53 | 0x80)));
		// e0,5b L-Win
		// e0,5c R-Win
		// e0,5d Context-Menu

		scancodeTable.put(new Integer(19), new Byte((byte) 0xFF)); // Pause
	}

	public byte getScancode(Integer keyCode) {
		try {
			return ((Byte) (scancodeTable.get(keyCode))).byteValue();
		} catch(NullPointerException e) {
			return (byte) 0x00;
		}
	}
}
