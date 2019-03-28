package com.itqf.entity;

import java.util.Date;

public class ScheduleJobLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.log_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private Long logId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.job_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private Long jobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.bean_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private String beanName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.method_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private String methodName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.params
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private String params;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.status
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.error
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private String error;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.times
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private Long times;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedule_job_log.create_time
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.log_id
     *
     * @return the value of schedule_job_log.log_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.log_id
     *
     * @param logId the value for schedule_job_log.log_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.job_id
     *
     * @return the value of schedule_job_log.job_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.job_id
     *
     * @param jobId the value for schedule_job_log.job_id
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.bean_name
     *
     * @return the value of schedule_job_log.bean_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.bean_name
     *
     * @param beanName the value for schedule_job_log.bean_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName == null ? null : beanName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.method_name
     *
     * @return the value of schedule_job_log.method_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.method_name
     *
     * @param methodName the value for schedule_job_log.method_name
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.params
     *
     * @return the value of schedule_job_log.params
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public String getParams() {
        return params;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.params
     *
     * @param params the value for schedule_job_log.params
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.status
     *
     * @return the value of schedule_job_log.status
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.status
     *
     * @param status the value for schedule_job_log.status
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.error
     *
     * @return the value of schedule_job_log.error
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public String getError() {
        return error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.error
     *
     * @param error the value for schedule_job_log.error
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.times
     *
     * @return the value of schedule_job_log.times
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public Long getTimes() {
        return times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.times
     *
     * @param times the value for schedule_job_log.times
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setTimes(Long times) {
        this.times = times;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedule_job_log.create_time
     *
     * @return the value of schedule_job_log.create_time
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedule_job_log.create_time
     *
     * @param createTime the value for schedule_job_log.create_time
     *
     * @mbggenerated Mon Mar 25 14:59:37 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}