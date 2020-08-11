/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tda.circulardobles;

/**
 *
 * @author davidmendozaloor
 */
public class CircularDoublyLinkedListNode<E> {
    private E content;
    private CircularDoublyLinkedListNode<E> next;
    private CircularDoublyLinkedListNode<E> previous;

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public CircularDoublyLinkedListNode<E> getNext() {
        return next;
    }

    public void setNext(CircularDoublyLinkedListNode<E> next) {
        this.next = next;
    }

    public CircularDoublyLinkedListNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(CircularDoublyLinkedListNode<E> previous) {
        this.previous = previous;
    }

    public CircularDoublyLinkedListNode(E content) {
        this.content = content;
        this.next = null;
        this.previous = null;
    }
    
    

}
