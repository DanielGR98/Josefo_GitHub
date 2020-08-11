/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tda.circulardobles;

import List.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author davidmendozaloor
 * @param <E>
 */
public class CircularDoublyLinkedList<E> implements List<E> {
    private CircularDoublyLinkedListNode<E> last;
    private int effective;
    
    
    
    public CircularDoublyLinkedListNode<E> getLastNode(){
        return last;
    }
    public CircularDoublyLinkedListNode<E> getFirstNode(){
        return last.getNext();
    }
    
    
    public void setLast(int index) {
        if(index>size() || index<0|| isEmpty()){
            throw new NoSuchElementException();
        }
        CircularDoublyLinkedListNode<E> nodo=last.getNext();
        int cont=0;
        do{
            if(index== cont){
                last=nodo;
                break;
            }
            nodo=nodo.getNext();
            cont++;
            
        }while(nodo!= last.getNext());
    }
    
    public void clear(){
        last=null;
        effective=0;
    }
    @Override
    public boolean addFirst(E element) {
        CircularDoublyLinkedListNode<E> nodo=new CircularDoublyLinkedListNode<>(element);
        if(element==null){
            return false;
        }else if(size()==0){
            last=nodo;
            last.setNext(last);
            last.setPrevious(last);
        }else{
            nodo.setNext(last.getNext());
            last.getNext().setPrevious(nodo);
            last.setNext(nodo);
            last.getNext().setPrevious(last);
            
        }
        effective++;
        return true;

    }

    @Override
    public boolean addLast(E element) {
        CircularDoublyLinkedListNode<E> nodo=new CircularDoublyLinkedListNode<>(element);
        if(element==null){
            return false;
        }else if(size()==0){
            last=nodo;
            last.setNext(last);
            last.setPrevious(last);
        }else{
            nodo.setNext(last.getNext());
            last.getNext().setPrevious(nodo);
            last.setNext(nodo);
            nodo.setPrevious(last);
            last=nodo;
            
        }
        effective++;
        return true;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty())return false;
        last.getNext().getNext().setPrevious(last);
        last.getNext().setPrevious(null);
        last.setNext(last.getNext().getNext());
        effective--;
        return true;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()) return false;
        int buff=0;
        for(CircularDoublyLinkedListNode<E> n=last.getNext();n!=last;n=n.getNext()){
            if(buff==size()-2){
                    n.setNext(last.getNext());
                    last.getNext().setPrevious(n);
                    last.setNext(null);
                    last.setPrevious(null);
                    last=n;
                    
                }
                buff++;
            }
        effective--;
        return true;
    }

    @Override
    public E getFirst() {
        return last.getNext().getContent();
    }

    @Override
    public E getLast() {
        return last.getContent();
    }

    @Override
    public boolean insert(int index, E element) {
        if(index>=size() || index<0)return false;
        else if(index==0) addFirst(element);
        else if(index== size()-1) addLast(element);
        else{
            CircularDoublyLinkedListNode<E> nodo= new CircularDoublyLinkedListNode<>(element);
            int buff=0;
            for(CircularDoublyLinkedListNode<E> n=last.getNext();n!=last;n=n.getNext()){
                if(buff==index-1){
                    nodo.setNext(n.getNext());
                    n.getNext().setPrevious(nodo);
                    n.setNext(nodo);
                    nodo.setPrevious(n);
                    
                    
                }
                buff++;
            }
            effective++;
        }
        
        return true;
    }

    @Override
    public boolean contains(E element) {
        if(element == null|| isEmpty()) return false;
        CircularDoublyLinkedListNode<E> nodo=last.getNext();
        do{
            if(element== nodo.getContent()) return true;
            nodo=nodo.getNext();
        }while(nodo!= last.getNext());
        return false;
    }

    @Override
    public E get(int index) {
        E valor=null;
        if(index>size() || index<0|| isEmpty()){
            return valor;
        }
        CircularDoublyLinkedListNode<E> nodo=last.getNext();
        int cont=0;
        do{
            if(index== cont){
                valor=nodo.getContent();
                break;
            }
            nodo=nodo.getNext();
            cont++;
            
        }while(nodo!= last.getNext());
        return valor;
    }

    @Override
    public int indexOf(E element) {
        int valor=-1;
        if(isEmpty()|| element==null) return valor;
        CircularDoublyLinkedListNode<E> nodo=last.getNext();
        int cont=0;
        do{
            if(element== nodo.getContent()){
                valor=cont;
                break;
            }
            nodo=nodo.getNext();
            cont++;
        }while(nodo!= last.getNext());
        return valor;
    }

    @Override
    public boolean isEmpty() {
        boolean valor= false;
        if(effective==0) valor=true;
        return valor;
    }

    @Override
    public E remove(int index) {
        E valorDev=null;
        if(index>= size() || index<0 || isEmpty()){
            return valorDev;
            
        }else if(index==0){
            valorDev=last.getNext().getContent();
            removeFirst();
            return valorDev;
        }else if(index== size()-1){
            valorDev=last.getContent();
            removeLast();
            return valorDev;
        }
        else{
            
            int cont=0;
            CircularDoublyLinkedListNode<E> n=last.getNext();
            do{
                if(cont== index-1){
                    valorDev=n.getNext().getContent();
                    n.getNext().getNext().setPrevious(n);
                    n.getNext().setPrevious(null);
                    n.setNext(n.getNext().getNext());
                    effective--;
                    return valorDev;
                }
                n=n.getNext();
                cont++;
            }while(n!= last.getNext());
            
        }
        return null;
    }

    @Override
    public boolean remove(E element, Comparator<E> comp) {
        boolean valorDev=false;
        CircularDoublyLinkedListNode<E> n=last.getNext();
        do{
           if(comp.compare(element,n.getContent())==0){
                valorDev=true;
                n.getPrevious().setNext(n.getNext());
                n.getNext().setPrevious(n.getPrevious());
                n.setNext(null);
                n.setPrevious(null);
                effective--;
                return valorDev;
            }
            n=n.getNext();
           
        }while(n!= last.getNext());
            
        
        return valorDev;
    }

    @Override
    public E set(int index, E element) {
        E valorDev=null;
        if(index>=size() || index<0 || element == null) return valorDev;
        CircularDoublyLinkedListNode<E> n=last.getNext();
        int cont=0;
        do{
            if(index ==cont){
                valorDev= n.getContent();
                n.setContent(element);
                return valorDev;
            }
            cont++;
            n=n.getNext();
        }while(n!=last.getNext());
        return valorDev;
    }

    @Override
    public int size() {
        return effective;
    }
    

    public ListIterator<E> listIterator(){
        return new ListIterator<E>() {

            CircularDoublyLinkedListNode<E> nodo=last.getNext();
            @Override
            public boolean hasPrevious(){
                boolean valor=false;
                if(size()>0) valor=true;
                return valor;
            }
            @Override
            public E previous(){
                if(!hasPrevious()){
                    throw new NoSuchElementException();
                }
                E valorDev= nodo.getContent();
                nodo=nodo.getPrevious();
                return valorDev;
            }
            @Override
            public boolean hasNext() {
                boolean valor=false;
                if(size()>0) valor=true;
                return valor;
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                E valorDev= nodo.getContent();
                nodo=nodo.getNext();
                return valorDev;
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int posNext=0;
            CircularDoublyLinkedListNode<E> nodoNext=last.getNext();
            int posPrevious=effective-1;
            CircularDoublyLinkedListNode<E> nodoPrevious=last;
            UnsupportedOperationException unsupport= new UnsupportedOperationException();
            @Override
            public boolean hasNext() {
                boolean valor=false;
                if(posNext<size() ) valor=true;
                return valor;
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                E valorDev= nodoNext.getContent();
                nodoNext=nodoNext.getNext();
                posNext++;
                return valorDev;
            }

        };
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        switch (size()) {
            case 0:
                sb.append("]");
                return sb.toString();
            case 1:
                sb.append(last.getNext().getContent().toString()).append("]");
                return sb.toString();
            default:
                CircularDoublyLinkedListNode<E> nodo=last.getNext();
                do{
                    if(nodo==last){
                        
                        sb.append(nodo.getContent().toString()).append("]");
                    }else{
                        sb.append(nodo.getContent().toString()).append(",");
                    }
                    nodo=nodo.getNext();
                }while(  nodo !=last.getNext());
                return sb.toString();
        }
    
    }   
}
