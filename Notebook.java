//Cory Wheeless
//3-18
package tmp;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Can store notes in a category arraylist or just in a note arraylist uncategorized
 * Cory		4/1		Back to using this class.
 * */

@SuppressWarnings("rawtypes")
public class Notebook 
	extends PackageCollection implements Serializable
{
	
	private static final long serialVersionUID = 45;
	
	@SuppressWarnings("unchecked")
	public void addNote(Note n)
	{
		super.addElement(n);
	}
	
	//Searches Notebook to find target within content
		public ArrayList<Note> findTarget(String target)
		{
			ArrayList<Note> result = new ArrayList<Note>();
			
			try{
			
			for(int i = 0; i < super.getSize(); i++)
			{
				Note tmp = (Note)super.getElement(i);
				String s = tmp.getContent();
				String targetLowerFirst = target.substring(0, 1).toLowerCase() + target.substring(1);
				String targetUpperFirst = target.substring(0, 1).toUpperCase() + target.substring(1);
				
				if(s.contains(targetUpperFirst) || s.contains(targetLowerFirst))
				{
					result.add(tmp);
				}
			}
			return result;
			
			}catch(StringIndexOutOfBoundsException e){
				
			}
			return null;
		}
		
		//Searches Notebook for title
		public ArrayList<Note> searchTitle(String target)
		{
			ArrayList<Note> result = new ArrayList<Note>();
			try{
			for(int i = 0; i < super.getSize(); i++)
			{
				Note tmp = (Note)super.getElement(i);
				String s = tmp.getTitle();
				String targetLowerFirst = target.substring(0, 1).toLowerCase() + target.substring(1);
				String targetUpperFirst = target.substring(0, 1).toUpperCase() + target.substring(1);
				
				if(s.contains(targetLowerFirst) || s.contains(targetUpperFirst))
				{
					result.add(tmp);
				}
			}
			return result;
			}catch(StringIndexOutOfBoundsException e){
				
			}
			return null;
		}
		

}
