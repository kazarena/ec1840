package com.binaryorder.embeddedpc.runner;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import org.jpc.emulator.PC;
import org.jpc.emulator.memory.Memory;
import org.jpc.emulator.processor.Processor;
import org.jpc.j2se.PCMonitorFrame;
import org.jpc.j2se.VirtualClock;
import org.jpc.test.Checkpoint;
import org.jpc.test.CheckpointCallback;
import org.jpc.test.CheckpointProcessor;

import com.binaryorder.embeddedpc.pc.IbmPC;

public class PCRunner {
	private static IbmPC runningPC;

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
		}

		if(args.length == 0)
			args = new String[] { "-fda", "mem:floppy.img", "-hda", "mem:dosgames.img", "-boot", "fda" };

		runningPC = IbmPC.createPC(args, new VirtualClock());
		PC pc = runningPC;

		// pc.getKeyboard().keyPressed((byte) 0x57);
		// pc.getKeyboard().keyReleased((byte) 0x57);

		// CheckpointProcessor.setCheckpoints(IbmPCTest.createPOSTCheckpoints(), false);

		List<Checkpoint> cpList = new ArrayList<Checkpoint>();
		cpList.add(new Checkpoint(0x7C46, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("Sector loaded.");
				Memory memoryBlock = runningPC.getPhysicalMemory().getReadMemoryBlockAt(0x7000);
				for(int i = 0; i < 16; i++) {
					for(int j = 0; j < 16; j++) {
						System.out.print(Integer.toHexString(0xff & memoryBlock.getByte(0xC00 + i * 16 + j)) + " ");
					}
					System.out.println();
				}
			}
		}));
		cpList.add(new Checkpoint(0xFE988, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 09 called");
				Memory memoryBlock = runningPC.getPhysicalMemory().getReadMemoryBlockAt(0x0000);
				byte kbdFlag2 = memoryBlock.getByte(0x418);
				memoryBlock.setByte(0x418, (byte) (kbdFlag2 | 0x02));
			}
		}, false));
		cpList.add(new Checkpoint(0xFEAF2, new CheckpointCallback() {
			public void checkpointPassed(Processor cpu) {
				System.out.println("INT 09 finished");
				Memory memoryBlock = runningPC.getPhysicalMemory().getReadMemoryBlockAt(0x0000);
				for(int i = 0; i < 16; i++) {
					for(int j = 0; j < 16; j++) {
						System.out.print(Integer.toHexString(0xff & memoryBlock.getByte(0x400 + i * 16 + j)) + " ");
					}
					System.out.println();
				}
			}
		}, true));
		CheckpointProcessor.setCheckpoints(cpList, false);

		PCMonitorFrame frame = PCMonitorFrame.createMonitor("JPC Monitor", pc, args);

	}
}
