package com.skilldistillery.jets;

public class FighterJet extends Jet implements CombatReady {

	public FighterJet(String model, double speed, int range, long price) {
		super(model, speed, range, price);
	}

	@Override
	public void fight() {
		System.out.println(this.toString() + " Firin' muh missiles! \n");

	}
	public void fly() {
		System.out.printf(
				this.getClass().getSimpleName() + " Model => " + getModel() + ", Speed => " + getSpeed() + " mph" + ", Range => "
						+ getRange() + ", Price => " + getPrice() + " Flight Time %.2f hours Mach= %.2f  \n",
						flightTime(), machSpeed());
	}

	@Override
	public String toString() {
		return "\n"+ getModel() + "\n(Speed => " + getSpeed() +" mph" + ", Range => " + getRange() + ", Price => " + getPrice()
				+ ", Class => " + getClass().getSimpleName() + ") \n";
	}



}