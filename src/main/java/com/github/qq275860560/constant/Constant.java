package com.github.qq275860560.constant;

/**
 * @author jiangyuanlin@163.com
 *
 */
public class Constant {
    public static final String DATAX_HOME = System.getenv("DATAX_HOME");    
   
    public static final int JOB_STATUS_DISABLE =0;//计划任务停用
    public static final int JOB_STATUS_ENABLE =1;//计划任务启用
    public static final int JOB_STATUS_RUNNING =2;//计划任务运行中    
    
    public static final int BUILD_STATUS_RUNNING =2;//构建中
    public static final int BUILD_STATUS_EXIT =3;//构建完毕
    
    public static final int BUILD_RESULT_SUCCESS = 1;//构建成功
    public static final int BUILD_RESULT_FAILURE = 2;//构建失败
    public static final int BUILD_RESULT_ABORTED = 3;//构建取消

    
}
