package com.binaryorder.embeddedpc.pc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.jpc.emulator.PC;
import org.jpc.emulator.processor.Processor;
import org.jpc.j2se.VirtualClock;
import org.jpc.test.Checkpoint;
import org.jpc.test.CheckpointCallback;
import org.jpc.test.CheckpointProcessor;

public class IbmPCTest extends TestCase {
	Logger logger = Logger.getLogger("IbmPCTest");

	public static List<Checkpoint> createPOSTCheckpoints() {
		List<Checkpoint> cpList = new ArrayList<Checkpoint>();

		cpList.add(new Checkpoint(0xffff0, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("POST Started");
			}
		}));
		cpList.add(new Checkpoint(0xfe05b, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("RESET");
			}
		}));
		cpList.add(new Checkpoint(0xFE0AF, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("FAILED");
				fail("Failed POST");
			}
		}));
		cpList.add(new Checkpoint(0xFE08E, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 01: 8088 PROCESSOR TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE0B1, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 02: ROS CHECKSUM TEST 1");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE0DB, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 03: 8237 DMA INITIALIZATION CHANNEL REGISTER TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE159, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 04: BASE 16K READ/WRITE STORAGE TEST");
			}
		}, false));
		// cpList.add(new Checkpoint(0xFE01A, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("STGTST DS=" +
		// Integer.toHexString(cpu.ds.getBase()) + "
		// ES="
		// + Integer.toHexString(cpu.es.getBase()));
		// }
		// }));
		// cpList.add(new Checkpoint(0xFE037, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("in STGTST AX=" + Integer.toHexString(cpu.eax));
		// }
		// }));
		cpList.add(new Checkpoint(0xFE1E2, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("FAILED");
				fail("Failed BASE 16K READ/WRITE STORAGE TEST");
			}
		}));
		cpList.add(new Checkpoint(0xFE5CB, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("KBD_RESET called");
			}
		}));
		cpList.add(new Checkpoint(0xFE5F4, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("KBD_RESET finished");
			}
		}));
		// cpList.add(new Checkpoint(0xFE5D8, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("SP_TEST called");
		// }
		// }));
		cpList.add(new Checkpoint(0xFE229, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 06: 8259 INTERRUPT CONTROLLER TEST");
			}
		}, false));
		// cpList.add(new Checkpoint(0xFE23A, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("TEST 06: 8259 INTERRUPT CONTROLLER TEST 2");
		// }
		// }, false));
		cpList.add(new Checkpoint(0xFE279, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 07: 8253 TIMER CHECKOUT");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE294, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 07 PHASE II");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE2A8, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEMP INTERRUPT CALLED");
			}
		}, true));
		cpList.add(new Checkpoint(0xFE2B6, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("NMI INTERRUPT CALLED");
			}
		}, true));
		cpList.add(new Checkpoint(0xFE272, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("FAILED at MANY BEEPS");
				fail("Failed at MANY BEEPS");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE2DE, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("ESTABLISH BIOS SUBROUTINE CALL INTERRUPT VECTORS");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE30C, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 08: INITIALIZE AND START CRT CONTROLLER (6845)");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE31F, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 08+ AX = " + Integer.toHexString(cpu.eax & 0xffff));
			}
		}, false));
		cpList.add(new Checkpoint(0xFE369, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 09: SETUP VIDEO DATA ON SCREEN FOR VIDEO LINE TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE37A, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 10: CRT INTERFACE LINES TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE3B2, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 11: ADDITIONAL READ/WRITE STORAGE TEST");
			}
		}, true));
		cpList.add(new Checkpoint(0xFE3C4, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 11 2");
			}
		}, true));
		// cpList.add(new Checkpoint(0xFE640, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("TEST 11 2");
		// }
		// }, true));
		// cpList.add(new Checkpoint(0xFE664, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("SIDE BRANCH 1");
		// }
		// }, true));
		cpList.add(new Checkpoint(0xFE698, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("WRITE CHAR " + Integer.toHexString(cpu.eax & 0xff));
			}
		}, true));
		cpList.add(new Checkpoint(0xFF066, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 10 called AX=" + Integer.toHexString(cpu.eax & 0xffff));
			}
		}, true));
		// cpList.add(new Checkpoint(0xFF3D3, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 2 AX=" + Integer.toHexString(cpu.eax &
		// 0xffff));
		// }
		// }, true));
		// cpList.add(new Checkpoint(0xFF3A7, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 FIND_POSITION AX=" +
		// Integer.toHexString(cpu.eax & 0xffff));
		// }
		// }, true));
		// cpList.add(new Checkpoint(0xFF40A, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 0xFF40A AX=" + Integer.toHexString(cpu.eax
		// & 0xffff));
		// }
		// }, true));
		// cpList.add(new Checkpoint(0xFF410, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 0xFF410 DX=" + Integer.toHexString(cpu.edx
		// & 0xffff));
		// }
		// }, true));
		cpList.add(new Checkpoint(0xFF583, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 10 GRAPHICS_WRITE");
			}
		}, true));
		cpList.add(new Checkpoint(0xFEC5A, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 13 called");
			}
		}, true));
		cpList.add(new Checkpoint(0xFE988, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 09 called");
			}
		}, true));
		// cpList.add(new Checkpoint(0xFF722, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 WRITE_TTY");
		// }
		// }, false));
		// cpList.add(new Checkpoint(0xFF729, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 WRITE_TTY 2");
		// }
		// }, false));
		// cpList.add(new Checkpoint(0xFF744, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("INT 10 WRITE_TTY 3");
		// }
		// }, false));
		// cpList.add(new Checkpoint(0xFF3F6, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("WRITE_C_CURRENT PROC");
		// }
		// }, true));
		// cpList.add(new Checkpoint(0xFF1C8, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("VIDEO_RETURN");
		// }
		// }, true));
		cpList.add(new Checkpoint(0xFE430, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 12: KEYBOARD TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE47D, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 13: CASSETTE DATA WRAP TEST - SKIPPED");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE482, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 14: DISKETTE ATTACHMENT TEST");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE492, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 14: DISKETTE ATTACHMENT TEST - PERFORM");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE57D, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 14: DISKETTE ATTACHMENT TEST - SKIPPED");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE4CC, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("0xFE4CC");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE4EB, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("SCREEN RESET");
			}
		}, false));
		cpList.add(new Checkpoint(0xFE4F6, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("TEST 15x: SETUP PRINTER AND RS232 BASE ADDRESSES IF DEVICE ATTACHED");
			}
		}, false));
		// cpList.add(new Checkpoint(0xFF96D, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("0xFF96D");
		// }
		// }, false));
		// cpList.add(new Checkpoint(0xFE55C, new CheckpointCallback() {
		// public void checkpointPassed(Processor cpu) {
		// System.out.println("0xFE55C");
		// }
		// }, false));
		cpList.add(new Checkpoint(0xFE5F8, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("Write_String_CR_LF called with si=" + Integer.toHexString(cpu.esi));
			}
		}, true));
		cpList.add(new Checkpoint(0xFE402, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("XLAT_PRINT_CODE al=" + Integer.toHexString(cpu.eax & 0x0f));
			}
		}, true));
		cpList.add(new Checkpoint(0xFE4E2, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("FAILED");
				// fail("Failed at ERROR (RESUME=\"F1\" KEY)");
			}
		}, false));
		return cpList;
	}

	public void testBIOS81() throws IOException {
		String[] args = new String[] { "-boot", "fda" };
		boolean running = true;
		PC pc = IbmPC.createPC(args, new VirtualClock());

		CheckpointProcessor.setCheckpoints(createPOSTCheckpoints());

		pc.start();
		long execCount = 0;
		try {
			while(running) {
				execCount += pc.execute();
			}
		} catch(Exception e) {
			System.err.println("Caught exception @ Address:0x"
					+ Integer.toHexString(pc.getProcessor().getInstructionPointer()));
			System.err.println(e);
			e.printStackTrace();
		} finally {
			pc.stop();
			System.err.println("PC Stopped");
		}
	}
}
