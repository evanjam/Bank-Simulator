import java.util.*;

public class Bank	//outer linkedlist class, entire program inside this class including main. creating instance of linked list means creating Bank object
{

	class Node //node class
	{

		public int arrival_time;
		public int service_time;
		public int total_time;
		public Node next;	//contains 3 pieces of data and a pointer to next node in LL
	}


	private Node first; //pointer to first node in list
	public int size = 0; //public variable to keep track of size, used for tracking # of nodes in tellerList[] (array of linked lists)

	public Bank()	//constructor
	{
		first = null;
	}

	public void insertFirst(int arr_time, int serv_time) //pass in data,data to get saved as arrival_time,service_time
	{
		Node n = new Node();
		n.arrival_time = arr_time;
		n.service_time = serv_time;
		n.next = first;
		first = n;
	}

	public void insertLast(int arr_time, int serv_time) //pass in data,data to get saved as arrival_time,service_time
	{
		Node n = new Node();
		n.arrival_time = arr_time;
		n.service_time = serv_time; 

		if (first == null)
		{
			insertFirst(arr_time, serv_time);
		}

		else
		{
			Node temp = first;
			while(temp.next != null)
				temp = temp.next;
			n.next = temp.next;
			temp.next = n;
		}
		size++; //increment size var as we add things to list
	}

	public Node removeFirst() //call on a linked list to remove first node. returns that node to be captured
	{
		if (first == null)
			throw new NoSuchElementException();

		Node temp = new Node();
		temp.arrival_time = first.arrival_time;
		temp.service_time = first.service_time;

		first = first.next;

		size--; //decr size as we remove nodes from list
		return temp;
	}

	public void fillList (Bank x)	//i had a great time typing this
	{ x.insertLast(0, 3); x.insertLast(0, 9); x.insertLast(1, 7); x.insertLast(2, 10); x.insertLast(3, 4); x.insertLast(4, 7); x.insertLast(5, 7); x.insertLast(5, 15); x.insertLast(6, 14); x.insertLast(12, 16); x.insertLast(14, 30); x.insertLast(17, 2); x.insertLast(19, 14); x.insertLast(22, 8); x.insertLast(22, 12); x.insertLast(23, 5); x.insertLast(24, 6); x.insertLast(25, 7); x.insertLast(27, 12); x.insertLast(30, 9); x.insertLast(33, 4); x.insertLast(34, 6); x.insertLast(36, 9); x.insertLast(38, 7); x.insertLast(39, 3); x.insertLast(40, 4); x.insertLast(41, 7); x.insertLast(41, 5); x.insertLast(42, 5); x.insertLast(42, 1); x.insertLast(42, 8); x.insertLast(42, 5); x.insertLast(45, 8); x.insertLast(47, 3); x.insertLast(48, 2); x.insertLast(49, 12); x.insertLast(50, 11); x.insertLast(50, 7); x.insertLast(51, 4); x.insertLast(56, 3);
	}

	public boolean isEmpty(Bank x) //pass a linkedlist object into it, checks if nodes have data in them. returns true if empty. 
	{
		if (x.first == null) //checks first node of linked list. if ==null, then it is empty
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean tellerListEmpty(Bank[] x) //pass an array of linked lists into it. chks if internal linkedlist nodes have content. returns true if empty
	{
		for(int i=0; i<x.length; i++) //iterates through each index of the array
		{
			if (x[i].first != null) //checks first node in each array for content.
				return false; //returns false if != null, meaning there is content
		}
		return true; //no content found, return true 
	}

	public static void main(String[] args)
	{
		System.out.println("\nEvan\tLab2\tBank Simulation Program\tFall 2017\n");

		Scanner keyin = new Scanner(System.in);	//set up scanner for user input

		Bank customerList = new Bank();		//create new linked list to store customers, a node is a customer
		customerList.fillList(customerList);	//fill customer list with preprovided data

		int timer = 0;				//create global timer for simulation
		int total_wait_time = 0;		//used later to sum up all customers total times
		int temp; 				//temp var used for user input
		do
		{
			System.out.println("How many tellers are staffed today?");
			temp = keyin.nextInt();	
			if(temp < 1)
			{
				System.out.print("enter a valid number greater than 1 dickhead\n");
			}
		} while(temp < 1);		//loop to take appropriate user input

		Bank tellerList[] = new Bank[temp];	//make array list of linked lists, each index is a teller
		for(int x=0;x<temp;x++)	//creates tellerList[] of correct size based on user input for each [index] in tellerList array. 
		{
			tellerList[x] = new Bank();	//creates linkedList at each index of tellerList [array]
		}

		do	//global loop and we need to force it to run on first iteration. conditions below at while statement
		{
			while(customerList.first != null && customerList.first.arrival_time == timer)	//loop to move custs to teller lines
			{	//condition is checking first cust's arrival time against global timer, other condition is to avoid reaching past for when list gets empty
				int tempShortest = 0;	//variable to assist with looping through tellers, needs to be reset for every iteration of this loop
				Node removedCust = customerList.removeFirst();	//removes first customer and stores in temp node 'removedCust'
				for(int x=1; x<temp; x++) //to find teller w/ shortestline. start at 1 so you dont compare 0 to 0, uses user input for #of iters
				{
					if (tellerList[x].size <= tellerList[tempShortest].size) //compare # custs in tellerList[1] with tellerList[var]. incr[var] on iter
					{
						tempShortest = x; //once condition is true you have found shortest line, set temp var to the iter var
					}
				}
				tellerList[tempShortest].insertLast(removedCust.arrival_time, removedCust.service_time); //insert that temp node at the iter var location
			} //when this loop completes a cust will be pulled from custLine and placed at shortest teller line, at the correct time of their arrival
			for(int x=0; x<temp; x++)	//loop through tellers again to see cust service time, iters based on user input
			{
				if (tellerList[x].first != null && tellerList[x].first.service_time != 0)	//if the cust still being helped
				{	//if service time is not 0 yet, meaning they are at the teller being helped
					tellerList[x].first.service_time--;	//decrement service time when cust is being served
					Node waitTemp = tellerList[x].first.next;	//temp node = to the 2nd person in teller line
					while(waitTemp != null) //while 2nd person in line exists
					{	//iterate through the teller's line
						waitTemp.total_time++; //incr wait time (total_time) of the cust
						waitTemp = waitTemp.next; //node is now pointing to the next person in teller's line
					} //by the time they are at the front of the teller line they will have accumulated a wait time (total_time)
				}
				if (tellerList[x].first != null && tellerList[x].first.service_time == 0) //if cust at front of teller line is done being serviced
				{
					total_wait_time = total_wait_time + tellerList[x].first.total_time; //take their total_time and add to global total time
					tellerList[x].removeFirst();	//remove them from tellers line
				}
			}//when loop cmpletes the cst at frnt of line will have been svc'd, his waittim added to global tot_time, and others waitng will incr wait time
			timer++;	//increase global timer on each iteration of global loop
		} while(!customerList.isEmpty(customerList) || !tellerListEmpty(tellerList)); //global loop. if customerList (LL) isnt empty and tellerList(LL[]) isnt empty

		double avg_time = total_wait_time / 40;	//calculate average time based on all the customer's total time / 40

		System.out.print("\n******************************\nGlobal timer: " + timer + " iterations\n");
		System.out.print("Total wait time: " + total_wait_time + "\nAverage wait time: " + avg_time + "\n\nSimulation Complete.\n");
		System.out.print("*****************************\n\n");
	}
}
