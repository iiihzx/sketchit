package hellomvc2;

import java.util.ArrayList;

public class Model {

    // the data in the model, just a counter
    private int counter;

    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();

    // method that the views can use to register themselves with the hellomvc2.Controller.Model
    // once added, they are told to update and get state from the hellomvc2.Controller.Model
    public void addView(IView view) {
        views.add( view);
        view.updateView(-9);
    }


    // simple accessor method to fetch the current state
    public int getCounterValue() {
        return counter;
    }

    // method that the Controller uses to tell the hellomvc2.Controller.Model to change state
    // in a larger application there would probably be multiple entry points like this
    public void incrementCounter() {
        System.out.println("hellomvc2.Controller.Model: increment counter to " + counter);
        notifyObservers();
    }

    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private void notifyObservers() {
        for (IView view : this.views) {
            System.out.println("hellomvc2.Controller.Model: notify View");
            view.updateView(-9);
        }
    }
}