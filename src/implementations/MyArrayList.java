package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {

	/**
	 * probably need to add getters and setters for tests(or just make the variables public)
	 */
	private static final long serialVersionUID = -7686414727616546634L;
	private static final int DEFAULT_LENGTH = 10;
    private Object[] elements;
    private int length = 0;
    
    public MyArrayList()
    {
    	elements = new Object[DEFAULT_LENGTH];
    }
    
    public MyArrayList(int arrayLength)
    {
    	elements = new Object[arrayLength];
    }
	
	@Override
	public int size() {
		return length;
	}

	/**comment: would prefer to simply create a new array and assign 
	 * it to "elements" but did it this way to be cautious
	 */
	@Override
	public void clear() {
		 for (int i = 0; i < elements.length; i++) {
		        elements[i] = null;
		    }
		 length = 0;
	}

	/**
	 * Unsure if it should throw IndexOutofBounds when elements is full
	 * so I added a new method that increases the array length so it appears
	 * dynamic like an arraylist
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (index < 0 || index > length)
		{
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		//if the length counter is equal to the length of the actual array, increases the size of the array
		if (elements.length == length)
		{
			addLength();
		}
		//moves the elements to the right by 1, starting at the end
		for (int i = length; i > index; i--)
		{
			elements[i] = elements[i - 1];
		}
		
		elements[index] = toAdd;
		length++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		return add(length, toAdd);
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		Iterator<? extends E> iterator = toAdd.iterator();
		while (iterator.hasNext())
			{
				add(iterator.next());
			}
		return true;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (E) elements[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
		
		E toRemove = (E) elements[index];
		for (int i = index; i < length - 1; i++) {
            elements[i] = elements[i + 1];
        }
		
		elements[length - 1] = null;
		length--;
        return (E) toRemove;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		for (int i = 0; i < length; i++) {
            if (elements[i].equals(toRemove)) {
                return remove(i);
            }
        }
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        E replaced = (E) elements[index];
        elements[index] = toChange;
        return replaced;
	}

	@Override
	public boolean isEmpty() {
		return length == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		for (int i = 0; i < length; i++) {
            if (elements[i].equals(toFind)) {
                return true;
            }
        }
        return false;
	}

	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold.length < length) {
            return (E[]) toArray();
        }
        for (int i = 0; i < length; i++) {
            toHold[i] = (E) elements[i];
        }
        return toHold;
	}

	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[length];
        System.arraycopy(elements, 0, newArray, 0, length);
        return newArray;
	}
//method I added to expand the array
	private void addLength()
	{
		Object[] array = new Object[elements.length + 1];
		System.arraycopy(elements, 0, array, 0, length);
		elements = array;
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ArrListIterator();
	}
	//nested class for the iterator
	private class ArrListIterator implements Iterator<E>
	{
		private int cursor;
		
		private ArrListIterator()
		{
			this.cursor = 0;
		}

		@Override
		public boolean hasNext() {
			return cursor < length;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext())
			{
				throw new NoSuchElementException("No more elements");
			}
			return (E) elements[cursor++];
		}
		
	}

}
