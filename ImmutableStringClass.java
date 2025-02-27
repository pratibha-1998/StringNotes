📌 Notes on Creating an Immutable Class in Java
1️⃣ What is an Immutable Class?
An immutable class is a class whose objects cannot be modified after creation.
Example: String class in Java.

2️⃣ Rules to Create an Immutable Class
✅ Declare the class as final → Prevents subclassing.
✅ Make all fields private final → Ensures they cannot be modified.
✅ Do not provide setters → No way to modify fields.
✅ Initialize fields via constructor → Assign values only once.
✅ Perform deep copy for mutable fields → Prevent external modifications.
✅ Return copies instead of original references → Prevent indirect changes.

3️⃣ Example of an Immutable Class
java
Copy
Edit
// Step 1: Declare class as final
final class Person {

    // Step 2: Make fields private and final
    private final int empId;
    private final String empName;
    private final Address address; // Mutable object

    // Step 3: Initialize fields via constructor
    public Person(int empId, String empName, Address address) {
        this.empId = empId;
        this.empName = empName;
        // Step 5: Deep copy for mutable object
        this.address = new Address(address.street(), address.city());
    }

    // Step 4: Only getter methods (No setters)
    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    // Step 6: Return deep copy of mutable object
    public Address getAddress() {
        return new Address(address.street(), address.city());
    }

    @Override
    public String toString() {
        return "Person [empId=" + empId + ", empName=" + empName + ", address=" + address + "]";
    }
}

// Mutable class
class Address {
    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String street() {
        return street;
    }

    public String city() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + "]";
    }
}
4️⃣ Why is this Class Immutable?
✅ Final class → Cannot be extended.
✅ Final fields → Values cannot be reassigned.
✅ No setters → No way to modify values.
✅ Deep copy of mutable objects → Prevents unintended changes.
✅ Returning copies instead of direct references → Ensures data security.

5️⃣ Testing the Immutability
java
Copy
Edit
public class ImmutableTest {
    public static void main(String[] args) {
        Address a1 = new Address("Bhosari", "Pune");
        Person p1 = new Person(1, "Rushikesh", a1);

        System.out.println("Before modification: " + p1);

        // Trying to modify the original address
        a1.setStreet("Changed Street");
        System.out.println("After modifying address: " + p1);

        // Trying to modify via getter method
        p1.getAddress().setCity("Mumbai");
        System.out.println("After modifying via getter: " + p1);
    }
}
Output:
less
Copy
Edit
Before modification: Person [empId=1, empName=Rushikesh, address=Address [street=Bhosari, city=Pune]]
After modifying address: Person [empId=1, empName=Rushikesh, address=Address [street=Bhosari, city=Pune]]
After modifying via getter: Person [empId=1, empName=Rushikesh, address=Address [street=Bhosari, city=Pune]]
✅ The address does not change even after modifying the Address object.

6️⃣ Common Mistake: Breaking Immutability
❌ Shallow Copy Instead of Deep Copy

java
Copy
Edit
// This makes class mutable because it stores a direct reference
public Person(int empId, String empName, Address address) {
    this.empId = empId;
    this.empName = empName;
    this.address = address; // ❌ BAD: Direct reference
}
🔴 Now modifying a1 will also modify p1's address!

7️⃣ Benefits of an Immutable Class
✅ Thread-Safe (No need for synchronization).
✅ Good for Caching (Objects can be safely reused).
✅ Easier to Maintain (No unexpected changes).
✅ Better HashMap Performance (Since keys do not change).

  ==========================================================================================
  Why is the Address Not Changing in Your Immutable Class?
The reason the address field inside your person class is not changing is because you are correctly creating a deep copy of the Address object in the constructor and getAddress() method. This ensures that any modifications made to the original Address object do not affect the person object.

Step-by-Step Breakdown of Your Code
1️⃣ Creating a Deep Copy in Constructor
java
Copy
Edit
person(int empId, String empName, Address address) {
    this.empId = empId;
    this.empName = empName;
    this.address = new Address(address.street(), address.city()); // Deep Copy
}
✅ This means person has its own separate copy of Address.
✅ Any changes made to a1 (the original Address object) won't reflect in p1.

2️⃣ Modifying the Original Address (a1)
java
Copy
Edit
a1.street("street changed");
System.out.println("after modifying address: " + p1);
Here, you are modifying a1, but p1 holds a separate copy of Address due to the deep copy created in the constructor.
Since p1's address is a different object, the modification does not affect p1.
3️⃣ Returning a Deep Copy in getAddress()
java
Copy
Edit
public Address getAddress() {
    return new Address(address.street(), address.city()); // Deep Copy
}
✅ If someone tries to modify the Address object obtained from p1.getAddress(), it will not affect the original person object.

🔴 How to Break This Immutability?
If you remove the deep copy in the constructor and getAddress() method, then modifying a1 would affect p1.

Breaking Immutability (BAD PRACTICE)
java
Copy
Edit
person(int empId, String empName, Address address) {
    this.empId = empId;
    this.empName = empName;
    this.address = address; // 🔴 Direct reference (Shallow Copy) - BAD
}
Now, if you modify a1, it WILL reflect inside p1.

🔵 How to Verify That Deep Copy is Working?
Try printing the memory addresses to confirm that p1's address is a separate object.

java
Copy
Edit
System.out.println("Original Address Object: " + a1);
System.out.println("Person's Address Object: " + p1.getAddress());
System.out.println("Are they the same object? " + (a1 == p1.getAddress())); // false ✅
Expected Output:

vbnet
Copy
Edit
Original Address Object: Address [street=bhosari, city=pune]
Person's Address Object: Address [street=bhosari, city=pune]
Are they the same object? false
This proves that p1 has its own separate Address object.

Conclusion
🔹 The address in p1 does not change because p1 contains a deep copy of the Address object.
🔹 Any changes to a1 will not affect p1's address.
🔹 This is the correct way to make an immutable class in Java.

  =====================================================================================================================

  Example: Immutable Person Class
java
Copy
Edit
// Step 1: Declare class as final
final class Person {

    // Step 2: Make fields private and final
    private final String name;
    private final int age;
    private final Address address; // Mutable object

    // Step 3: Initialize all fields through constructor
    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        // Step 5: Perform deep copy for mutable objects
        this.address = new Address(address.getStreet(), address.getCity());
    }

    // Step 4: Provide only getter methods (No setters)
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Step 6: Return a deep copy instead of direct reference
    public Address getAddress() {
        return new Address(address.getStreet(), address.getCity());
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", address=" + address + "}";
    }
}

// Helper Mutable Class
class Address {
    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    // Getters
    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    // Setters (Mutable class)
    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{street='" + street + "', city='" + city + "'}";
    }
}
Testing the Immutability
java
Copy
Edit
public class ImmutableTest {
    public static void main(String[] args) {
        Address address = new Address("Main Street", "New York");
        Person person = new Person("John", 25, address);

        System.out.println("Before modification: " + person);

        // Trying to modify the address field
        address.setStreet("Changed Street");
        System.out.println("After modifying original address object: " + person);

        // Trying to modify via getter (This should not affect original object)
        person.getAddress().setCity("Los Angeles");
        System.out.println("After modifying via getter: " + person);
    }
}


