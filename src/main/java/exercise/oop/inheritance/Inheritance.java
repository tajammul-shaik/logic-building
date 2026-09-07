package exercise.oop.inheritance;

// Base Superclass
class Animal{
    String name;

    Animal(String name){
        this.name = name;
    }

    void eat(){
        System.out.println(name +" is eating food.");
    }

}

//Subclass 1 : Single Inheritance: Dog IS-A Animal
class Dog extends Animal {
     Dog(String name){
         super(name); // Invokes parent constructor
     }

     void bark(){
         System.out.println(name + " says: Woof! ");
     }

}

//Subclass 2 : Multilevel Inheritance: Labrador IS-A Dog IS-A Animal
class Labrador extends Dog {

    Labrador(String name){
        super(name);
    }

    //Method overriding
    @Override
    void eat(){
        System.out.println(name + " eats regularly");
    }

}


public class Inheritance {

    public static void main(String[] args) {
        Labrador labrador = new Labrador("buddy");

        labrador.eat();
        labrador.bark();
    }
}
