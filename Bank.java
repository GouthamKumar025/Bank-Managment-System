import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Scanner;

class Account implements Serializable{
    String name;
    int account_number;
    String pin;
    double Amount;
    Account(){
        name=null;
        account_number=0;
        pin=null;
        Amount=0;
    }
    Account(String n,int acc,String pin_no,double amount){
        name=n;
        account_number=acc;
        pin=pin_no;
        Amount=1000+amount;
    }
    public void setName(String n){
        name=n;
    }
    public void setAccount_no(int acc){
        account_number=acc;
    }
    public void setPin(String pin_no){
        pin=pin_no;
    }
    public void setAmount(double a){
        Amount=a;
    }
    public String getName(){
        return name;
    }
    public int getAccount_no(){
        return account_number;
    }
    public String getpin(){
        return pin;
    }
    public double getAmount(){
        return Amount;
    }
}
class BankTasks{
    ArrayList<Account>AI = new ArrayList<Account>();
    
    // Adding new Records in Bank
    public void addNewRecord(){
        Scanner input=new Scanner(System.in);
        System.out.print("\nEnter the name of Account Holder: ");
        String n=input.nextLine();
        System.out.print("Enter an 8 digit Account Number: ");
        int a=input.nextInt();
        System.out.print("Enter PIN of the Account Holder: ");
        String b=input.next();
        System.out.print("INR 1000 is already credited to the account");
        System.out.print("To add more money enter it , else enter 0 ");
        double c=input.nextDouble();
        

        Account ac=new Account(n,a,b,c);
        AI.add(ac);
        return;
    }

    // Transfer of money
    public void transfer(){
        Scanner input=new Scanner(System.in);
        System.out.print("\nEnter Sender's Account Number: ");
        int s_acc=input.nextInt();
        System.out.print("\nEnter Sender's pin code: ");
        String s_pin=input.next();
        int s_index=-1;
        for(int i=0;i<AI.size();i++){
            if(AI.get(i).getAccount_no()==s_acc && AI.get(i).getpin().equals(s_pin)){
                s_index=i;
            }
        }
         if(s_index==-1){
            System.out.print("\nAccount not Found");
            return;
         }
        System.out.println("\nEnter receiver's Account Number: ");
        int r_acc=input.nextInt();
        int r_index=-1;
        for(int i=0;i<AI.size();i++){
            if(AI.get(i).getAccount_no()==r_acc){
                 r_index=i;
            }
        }
        if(r_index==-1){
            System.out.println("\nReceiver's account not found");
            return;
        }
        System.out.print("\nAmount to be transferred: ");
        double amount=input.nextDouble();
        if(AI.get(s_index).getAmount()>=amount){
            AI.get(r_index).setAmount(AI.get(r_index).getAmount()+ amount);
            AI.get(s_index).setAmount(AI.get(s_index).getAmount()-amount);
            return;
         }
        else{
            System.out.println("You have insufficient Balance");
            return;
        }
            
    }
    //Withdrawal of money
    public void withdraw(){
        Scanner input=new Scanner(System.in);
        System.out.print("\nEnter User's account number: ");
        int p_acc=input.nextInt();
        System.out.print("Enter User's pin code: ");
        String p_pin=input.next();

        int p_index=-1;
        for(int i=0;i<AI.size();i++){
            if((AI.get(i).getAccount_no()==p_acc)&& (AI.get(i).getpin().equals(p_pin))){
                p_index=i;
            }
        }
        if(p_index==-1){
            System.out.println("\nAccount not found");
            return;
        }
        System.out.print("\nAmount to be withdrawn: ");
        double amount=input.nextDouble();
        if(AI.get(p_index).getAmount()>=amount){
            AI.get(p_index).setAmount(AI.get(p_index).getAmount()-amount);
            return;
        }
        else{
            System.out.println("\nInsufficient Balance");
            return;
        }
    }
    //Printing the Customer details
    public void print()
	{
		for(int i = 0; i<AI.size(); i++)
		{
			System.out.println("\nName: " + AI.get(i).getName());
			System.out.println("Account Number: " + AI.get(i).getAccount_no());
			System.out.println("Balance: " + AI.get(i).getAmount() + "\n");
		}
	}
    // Loading the data's in a file
    public void load()
	{
	 try{
		FileInputStream fis = new FileInputStream("BankRecord.txt");
		ObjectInputStream in = new ObjectInputStream(fis);
		while(true)
		{
			Account temp = (Account) in.readObject(); 
			if(temp == null)
				break;
			AI.add(temp);
		}
		fis.close();
	     }
	 catch(Exception e)
	 {
	 }
	}
	
	public void save()
	{
	 try{
		FileOutputStream fos = new FileOutputStream("BankRecord.txt");
		ObjectOutputStream out = new ObjectOutputStream(fos);
		for(int i = 0; i<AI.size(); i++)
			out.writeObject(AI.get(i));
		fos.close();
	    }
	 catch(Exception e)
	 {
		System.out.println("\nError Saving Data to File");
	 }	
	}
}
class Bank{
    public static void main(String[]args){
        BankTasks obj=new BankTasks();
        obj.load();
        try{
            Scanner input=new Scanner(System.in);
            System.out.println("\n*****Welcome to ABC Bank*****");
            int choice=0;
            while(choice!=5){
                System.out.println("\n1 - Create new Account");
			System.out.println("2 - Transfer money from an existing account to another existing account");
			System.out.println("3 - Withdraw money from existing account");
			System.out.println("4 - Print all exisitng accounts");
			System.out.println("5 - Exit");
			System.out.println("Enter your choice: ");
			choice = input.nextInt();

			if(choice == 1)
			{
				obj.addNewRecord();
				System.out.println("\nAccount Created Successfully");
			}
			else if(choice == 2)
			{
				obj.transfer();
			}
			else if(choice == 3)
			{
				obj.withdraw();
			}
			else if(choice == 4)
				obj.print();
			else if(choice == 5)
			{
				obj.save();
				System.out.println("\nData saved to File \"BankRecord .txt\"");
				System.exit(1);
			}
			else
				System.out.println("\nWrong Input");
                
		    }
        }
        catch(Exception e){
            System.out.println("\nError");
        }
        finally{
            obj.save();
        }
    }
}
 
