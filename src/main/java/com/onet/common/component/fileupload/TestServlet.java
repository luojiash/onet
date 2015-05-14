package com.onet.common.component.fileupload;

public class TestServlet {
	public synchronized void test() {
		System.out.println("sync begin");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		System.out.println("sync end");
	}

	public void test1() {
		System.out.println("non sync block");
	}

	public static void main(String[] args) {
		TestServlet testServlet = new TestServlet();
		for (int i = 0; i < 3; i++) {
			new MyThread(testServlet, i).start();
		}
	}
}

class MyThread extends Thread {
	TestServlet testServlet;
	int i;

	public MyThread(TestServlet testServlet, int i) {
		this.testServlet = testServlet;
		this.i = i;
	}

	public void run() {
		System.out.println(i);
		if (i % 2 == 0) {
			testServlet.test();
		} else {
			testServlet.test1();
		}
	}
}