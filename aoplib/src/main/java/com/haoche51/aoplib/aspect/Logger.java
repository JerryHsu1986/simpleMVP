package com.haoche51.aoplib.aspect;

import android.os.Build;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * @author HaiboXu on 2018/11/7
 * @github https://github.com/JerryHsu1986
 */
@Aspect
public class Logger {
    private static volatile boolean enable = true;

    @Pointcut("within(@com.haoche51.aoplib.DebugLog *)")
    public void withAnnotatedClass(){} // with class

    @Pointcut("execution(!synthetic * * (..))&& withAnnotatedClass()")
    public void methodInsideAnnotatedType(){} // inside method

    @Pointcut("execution(!synthetic *.new(..)) && withAnnotatedClass()")
    public void constructorAnnotatedType(){} //constructor

    @Pointcut("execution(@com.haoche51.aoplib.DebugLog * *(..)) || methodInsideAnnotatedType()")
    public void method(){}

    @Pointcut("execution(@hugo.weaving.DebugLog *.new(..)) || constructorAnnotatedType()")
    public void constructor() {} //new

    @Around("method() || constructor()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        enter(joinPoint);
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long stopTime = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopTime - startTime);
        exit(joinPoint,result,lengthMillis);
        return result;
    }

    private static void enter(JoinPoint joinPoint){
        if (!enable) return;
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
        String[] argsName = signature.getParameterNames();
        Object[] argsVal  = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder("\u21E2 ");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]\u21E2");
        }
        builder.append(methodName).append('(');
        for (int i=0;i<argsVal.length;i++){
            if (i>0){
                builder.append(", ");
            }
            builder.append(argsName[i]).append("=");
            builder.append(Strings.toString(argsVal[i]));
        }
        builder.append(")");
        Log.v(asTag(cls),builder.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            final String section = builder.toString().substring(2);
            Trace.beginSection(section);
        }
    }

    private static void exit(JoinPoint joinPoint,Object result,long millis){
        if (!enable) return ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }
        Signature signature = joinPoint.getSignature();
        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature
                && ((MethodSignature) signature).getReturnType() != void.class;

        StringBuilder builder = new StringBuilder("\u21E0 ")
                .append(methodName)
                .append(" [")
                .append(millis)
                .append("ms]");

        if (hasReturnType) {
            builder.append(" = ");
            builder.append(Strings.toString(result)); //thanks jakewharton's strings
        }
        Log.v(asTag(cls), builder.toString());
    }

    private static String asTag (Class<?> cls){
        if (cls.isAnonymousClass()){
            return asTag(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }
}
