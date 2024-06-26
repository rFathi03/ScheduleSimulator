package cpuScheduler;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class ScheduleSimulator {    
	private List<Process> processes;
	public float avgWaitingTime;
	public float avgTurnAroundTime;

	public ScheduleSimulator() {
    processes = new ArrayList<>();
	float avgWaitingTime = 0;
	float avgTurnAroundTime = 0;
	}
    
    public static void main(String[] args) {
        ScheduleSimulator scheduleSimulator = new ScheduleSimulator();
        scheduleSimulator.run();
    }
    
    
    public List<Process> getProcesses() {
        return processes;
    }

	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the number of processes.");
		int noOfProcesses = scanner.nextInt();
		System.out.println("Please enter the Round Robin Time Quantum.");
		int rrQuantumTime = scanner.nextInt();		
		System.out.println("Please enter the context switching time.");
		int contextSwitchTime = scanner.nextInt();
		List<Process> processes = new ArrayList<>();

		for(int i=0;i<noOfProcesses;i++) {
            System.out.println("Please enter the details of process number " + (i + 1));
            System.out.print("Process Name: ");
            String name = scanner.next();

            System.out.print("Process Color (Graphical Representation): ");
            String color = scanner.next();

            System.out.print("Process Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Process Burst Time: ");
            int burstTime = scanner.nextInt();

            System.out.print("Process Priority Number: ");
            int priority = scanner.nextInt();
            processes.add(new Process(name, color, arrivalTime, burstTime,priority));
		}
		// Select scheduler type and simulate
        System.out.println("Select Scheduler Type:");
        System.out.println("1. Non-Preemptive Shortest- Job First (SJF)");
        System.out.println("2. Shortest- Remaining Time First (SRTF) Scheduling");
        System.out.println("3. Non-preemptive Priority Scheduling");
        System.out.println("4. AG Scheduling");

        int schedulerType = scanner.nextInt();
        List<Pair<Process, Integer>> execOrder = new ArrayList<>();
        
        switch (schedulerType) {
            
        case 1:
            // Non-Preemptive SJF Scheduler Function
        	//NonPreemptiveSJF scheduler = new NonPreemptiveSJF();
        	//scheduler.schedule(processes, contextSwitchTime);
        	NonPreemptiveSJF.schedule(processes, contextSwitchTime);
        	execOrder = NonPreemptiveSJF.getExecutionOrder();
        	avgWaitingTime = NonPreemptiveSJF.getAvgWaiting(); 
        	avgTurnAroundTime = NonPreemptiveSJF.getAvgTurnAround();
            break;

        case 2:
            // Call Shortest- Remaining Time First Scheduler Function
            SRTF srtfScheduler = new SRTF(processes, contextSwitchTime);
            srtfScheduler.runScheduler();
            execOrder = srtfScheduler.getExecutionOrder();
            avgWaitingTime = srtfScheduler.getAvgWaiting(); 
        	avgTurnAroundTime = srtfScheduler.getAvgTurnAround();
            break;

        case 3:
            // Call Non-preemptive Priority Scheduler Function
        	NonPreemptivePS.schedule(processes, contextSwitchTime);
        	execOrder = NonPreemptivePS.getExecutionOrder();
        	avgWaitingTime = NonPreemptivePS.getAvgWaiting(); 
        	avgTurnAroundTime = NonPreemptivePS.getAvgTurnAround();
        	
        	// I'm implementing the printing inside the function itself since the printing process 
        	// will differ from scheduler to scheduler
            break;

        case 4:
        AGScheduling.schedule(processes, rrQuantumTime);
        execOrder = AGScheduling.getExecutionOrder();
        avgWaitingTime = AGScheduling.getAverageWaitingTime(); 
        avgTurnAroundTime = AGScheduling.getAverageTurnaroundTime();
            
        break;

        default:
            System.out.println("Please enter a type number, i.e 1 or 2 or 3 or 4.");
        }
        
    	ProcessGUIColors processGUI = new ProcessGUIColors(execOrder);
        // Update the GUI with the calculated statistics
        processGUI.setAvgWaitingTime(avgWaitingTime);
        processGUI.setAvgTurnAroundTime(avgTurnAroundTime);
        processGUI.setSchedulerType(schedulerType);
    	processGUI.setVisible(true);
	}

}


