import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.AbstractAction;

public class WorkerDemo extends JFrame {
	private boolean canceled = false;
	private boolean paused = false;
	private JLabel counterLabel = new JLabel("Not started");
	private Worker worker = new Worker();
	private int counter = 0; 
	
	private JButton startButton = new JButton(new AbstractAction("Start") {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (canceled && !paused) {
				worker = new Worker();
			}
			worker.execute();

			// buttons
			startButton.setEnabled(false);
			pauseButton.setEnabled(true);
			stopButton.setEnabled(true);
			
			if (paused){
				resume();
			}
		}

	});
	
	private JButton pauseButton = new JButton(new AbstractAction("Pause"){
		@Override
		public void actionPerformed(ActionEvent e) {
			pause();
			
			// buttons
			startButton.setEnabled(true);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(true);
		}
		
	});
	
	private JButton stopButton = new JButton(new AbstractAction("Stop") {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			worker.cancel(true);
			//counterLabel.setText("0");
			counter = 0;
		
			canceled = true;
			paused = false;
			
			// buttons
			startButton.setEnabled(true);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(false);
		}

	});
	
	public void pause(){
		paused = true;
	}
	
	public synchronized void resume(){
		System.out.println("Resume work");
		paused = false;
		this.notify();
	}

	public WorkerDemo() {

		add(startButton, BorderLayout.WEST);
		add(counterLabel, BorderLayout.CENTER);
		add(pauseButton, BorderLayout.SOUTH);
		add(stopButton, BorderLayout.EAST);
		pack();
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	class Worker extends SwingWorker<Void, Integer> {

		//int counter = 0;

		@Override
		protected Void doInBackground() throws Exception {
			while (!isCancelled()) {
				if(paused){
					System.out.println("BackGround paused, waiting for resume");
					try{
						synchronized(this){
							wait(1000);
						}
					} catch(InterruptedException ex){
						System.out.println("Background interrupted");
					}
				} else{
					System.out.println("Background Running");
					// background work 
					//counter++;
					publish(counter);
					Thread.sleep(60); //why?
				}
			}
			return null;
		}

		@Override
		protected void process(List<Integer> chunk) {
			System.out.println("process...");
			// get last result
			//Integer counterChunk = chunk.get(chunk.size() - 1);
			counter ++;
			counterLabel.setText(counter +"");
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new WorkerDemo();
			}

		});
	}

}