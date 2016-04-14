package tmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class PackageCollection<T> 
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 45;

	private final static int DEFAULT_CAPACITY = 25;
	
	private int front;
	private ArrayList<T> collection;
	
	public PackageCollection()
	{
		this(DEFAULT_CAPACITY);
		front = 0;
	}
	
	public PackageCollection(int initialCapacity)
	{
		front = 0;
		collection = new ArrayList<T>(initialCapacity);
	}
	
	public void addElement(T element) 
	{
		collection.add(element);
		front++;
	}
	
	public ArrayList<T> getCollection()
	{
		return collection;
	}
	
	public int getSize()
	{
		return collection.size();
	}
	
	//for gui countEvents to work
	public T getElement(int index)
	{
		return collection.get(index);
	}

	public void save(String fileName) 
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(new File("sers/" + fileName + ".ser"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Everything Worked!");
		}
		catch(IOException i)
		{
			System.out.println("Everything Broke");
			i.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load(String fileName)
	{
		PackageCollection result = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("sers/" + fileName + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			result = (PackageCollection)in.readObject();
			for(int i = 0; i < result.getSize(); i++)
			{
				T element = (T)result.collection.get(i);
				this.addElement(element);
			}
			fileIn.close();
			in.close();
		}
		catch(IOException i)
		{
			System.out.println("Everything broke");
			i.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Everything broke");
		}
	}
	
	public String toString()
	{
		String result = "";
		
		for(int i = 0; i < front; i++)
		{
			result += collection.get(i);
		}
		return result;
	}

}
