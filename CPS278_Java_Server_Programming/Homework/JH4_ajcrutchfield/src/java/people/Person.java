/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package people;

/**
 *
 * @author AaronC
 */

// Name, eye color, hair color, height, and weight

public class Person {
    private String name;
    private String eyeColor;
    private String hairColor;
    private String height;
    private String weight;
    
    public Person(String name, String eyeColor, String hairColor, String height, String weight){
        this.name = name;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.height = height;
        this.weight = weight;
    }
    
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        
        if (other.getClass() != getClass()){
            return false;
        }
        
        Person otherPerson = (Person)other;
        if (name.equals(otherPerson.name) && eyeColor.equals(otherPerson.eyeColor) &&
                hairColor.equals(otherPerson.hairColor) && height.equals(otherPerson.height) &&
                        weight.equals(otherPerson.weight))
            return true;
        else
            return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    
    
}
