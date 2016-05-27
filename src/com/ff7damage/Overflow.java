package com.ff7damage;

import java.math.BigInteger;

public class Overflow {
	private final BigInteger aoValue = new BigInteger("2147483648");
	private BigInteger bdNumerator;
	private int overflowableBDNumerator;
	private int[] bdOverflows; //first the overflows (to negative), then the anti-overflows (to positive)
	private boolean bdPositive;
	private BigInteger postRandomVarianceNumerator;
	private int overflowablePostRandomVarianceNumerator;
	private int[] positiveBDrvOverflows; //first the overflows (to negative), then the anti-overflows (to positive)
	private int[] negativeBDrvOverflows; //first the anti-overflows (back to positive), then the overflows (to negative)
	private boolean afterRVDamagePositive;
	
	public Overflow() {
		this.bdOverflows = new int[]{0, 0};
		this.positiveBDrvOverflows = new int[]{0, 0};
		this.negativeBDrvOverflows = new int[]{0, 0};
		this.bdPositive = true;
		this.afterRVDamagePositive = true;
	}
	
	public BigInteger getBdNumerator() {
		return this.bdNumerator;
	}

	public void setBdNumerator(BigInteger bdNumerator) {
		this.bdNumerator = bdNumerator;
	}

	public int getOverflowableBDNumerator() {
		return this.overflowableBDNumerator;
	}

	public void setOverflowableBDNumerator(int overflowableBDNumerator) {
		this.overflowableBDNumerator = overflowableBDNumerator;
		this.bdPositive = overflowableBDNumerator >= 0;
	}

	public int[] getBdOverflows() {
		return this.bdOverflows;
	}

	public boolean isBdPositive() {
		return this.bdPositive;
	}

	public BigInteger getPostRandomVarianceDamage() {
		return this.postRandomVarianceNumerator;
	}

	public void setPostRandomVarianceNumerator(BigInteger postRandomVarianceNumerator) {
		this.postRandomVarianceNumerator = postRandomVarianceNumerator;
	}

	public int getOverflowablePostRandomVarianceNumerator() {
		return this.overflowablePostRandomVarianceNumerator;
	}

	public void setOverflowablePostRandomVarianceNumerator(int overflowablePostRandomVarianceNumerator) {
		this.overflowablePostRandomVarianceNumerator = overflowablePostRandomVarianceNumerator;
		this.afterRVDamagePositive = overflowablePostRandomVarianceNumerator >= 0;
	}

	public int[] getPositiveBDrvOverflows() {
		return this.positiveBDrvOverflows;
	}

	public int[] getNegativeBDrvOverflows() {
		return this.negativeBDrvOverflows;
	}
	
	public boolean isAfterRVDamagePositive() {
		return this.afterRVDamagePositive;
	}

	public BigInteger getAoValue() {
		return this.aoValue;
	}
	
	public void calculateBDOverflows() {
		BigInteger temp = this.bdNumerator.subtract(this.aoValue);
		
		while(temp.signum() >= 0) {
			this.bdOverflows[0]++;
			temp = temp.subtract(this.aoValue);
			
			if(temp.signum() >= 0) {
				this.bdOverflows[1]++;
			}
			
			temp = temp.subtract(this.aoValue);
		}
	}
	
	public void calculateRVOverflows() {
		BigInteger temp = this.postRandomVarianceNumerator.subtract(this.aoValue);
		
		if(this.bdPositive) {
			while(temp.signum() >= 0) {
				this.positiveBDrvOverflows[0]++;
				temp = temp.subtract(this.aoValue);
				
				if(temp.signum() >= 0) {
					this.positiveBDrvOverflows[1]++;
				}
				
				temp = temp.subtract(this.aoValue);
			}
		}
		else {			
			while(temp.signum() > 0) {
				this.negativeBDrvOverflows[0]++;
				temp = temp.subtract(this.aoValue);
				
				if(temp.signum() > 0) {
					this.negativeBDrvOverflows[1]++;
				}
				
				temp = temp.subtract(this.aoValue);
			}
		}
	}
}