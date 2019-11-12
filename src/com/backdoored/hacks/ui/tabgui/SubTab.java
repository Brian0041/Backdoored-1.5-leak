package com.backdoored.hacks.ui.tabgui;

public class SubTab<T>
{
    private String text;
    private TabActionListener<T> listener;
    private T object;
    
    public SubTab(final String text, final TabActionListener<T> listener, final T object) {
        super();
        this.text = text;
        this.listener = listener;
        this.object = object;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public TabActionListener<T> getListener() {
        return this.listener;
    }
    
    public void setListener(final TabActionListener<T> listener) {
        this.listener = listener;
    }
    
    public T getObject() {
        return this.object;
    }
    
    public void setObject(final T object) {
        this.object = object;
    }
    
    public void press() {
        if (this.listener != null) {
            this.listener.onClick(this);
        }
    }
    
    public interface TabActionListener<T>
    {
        void onClick(final SubTab<T> p0);
    }
}
