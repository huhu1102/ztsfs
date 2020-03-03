/**  
* 
*/  
 
package com.zt.model;



/**
 * @author whl
 * @date 2019年5月7日
 *  
 */
public class ProcessModel {

	private String currentSteps;
	//工序Id
	private long preProcessId;
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getPreProcessId() {
        return preProcessId;
    }

    public void setPreProcessId(long preProcessId) {
        this.preProcessId = preProcessId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentSteps
	 */
	/**
	 * @return the currentSteps
	 */
	public String getCurrentSteps() {
		return currentSteps;
	}
	/**
	 * @param currentSteps the currentSteps to set
	 */
	public void setCurrentSteps(String currentSteps) {
		this.currentSteps = currentSteps;
	}
	
	
}
