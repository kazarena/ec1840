package com.binaryorder.embeddedpc.runner;

import javax.swing.UIManager;

import org.jpc.emulator.PC;
import org.jpc.j2se.PCMonitorFrame;
import org.jpc.j2se.VirtualClock;
import org.jpc.test.CheckpointProcessor;

import com.binaryorder.embeddedpc.pc.IbmPC;
import com.binaryorder.embeddedpc.pc.IbmPCTest;

public class PCRunner {
	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
		}

		if(args.length == 0)
			args = new String[] { "-fda", "mem:floppy.img", "-hda", "mem:dosgames.img", "-boot", "fda" };

		PC pc = IbmPC.createPC(args, new VirtualClock());

		CheckpointProcessor.setCheckpoints(IbmPCTest.createPOSTCheckpoints());

		PCMonitorFrame frame = PCMonitorFrame.createMonitor("JPC Monitor", pc, args);

	}
}
