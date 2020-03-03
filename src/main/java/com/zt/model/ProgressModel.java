/**  
* 
*/  
 
package com.zt.model;

/**
 * @author whl
 * @date 2019年5月9日 
 */
public class ProgressModel {

	private long  stepNumber;//工序步骤编号
    private long  step;//工序步骤
    private String  stepType;//类型 :"delete"
	/**
	 * @return the stepNumber
	 */
	public long getStepNumber() {
		return stepNumber;
	}
	/**
	 * @param stepNumber the stepNumber to set
	 */
	public void setStepNumber(long stepNumber) {
		this.stepNumber = stepNumber;
	}
	/**
	 * @return the step
	 */
	public long getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(long step) {
		this.step = step;
	}
	/**
	 * @return the stepType
	 */
	public String getStepType() {
		return stepType;
	}
	/**
	 * @param stepType the stepType to set
	 */
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	
}
