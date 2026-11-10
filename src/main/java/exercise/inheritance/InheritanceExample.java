package exercise.inheritance;

//
/// / Parent / Base / Superclass
//class Animal {
//    String name;
//
//    void eat() {
//        System.out.println(name + " is eating.");
//    }
//}
//

//// Child / Derived / Subclass
//class Dog extends Animal {
//    void bark() {
//        System.out.println(name + " is barking.");
//    }
//}
    class prajati{
        prajati(){
            System.out.println("prajati no arg constructor");
        }
}

class Animal extends prajati{

    Animal() {
        System.out.println("animal no arg constructor");
    }

    Animal(int num){
        this();
        System.out.println("int cons param"+ num);
    }


    Animal(String name, int num) {
        this(num);
//        this(num);
        System.out.println("Animal constructor called: " + name);
    }

    void print(){
        System.out.println("Hello Animal");
    }
}

class Dog extends Animal {
    Dog(String name,int num) {
//        super(); // can not be used two super(), if want both constructors logic use this keyword in above
        super(name, num); // Explicitly calls Animal(String name)
        System.out.println("Dog constructor called");
    }
}

public class InheritanceExample {

    public static void main(String[] args) {
//        Dog myDog = new Dog();
//        myDog.name = "Buddy"; // Inherited from Animal
//        myDog.eat();          // Inherited from Animal
//        myDog.bark();         // Defined specifically in Dog

        Dog dog = new Dog("dog", 10);
//        dog.Animal();

    }
}
