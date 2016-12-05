package chaos.core.web.exception.aspect;

import chaos.core.model.CaseRes;
import chaos.core.web.exception.ExceptionHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-03-18
 */
// @Aspect : 标记为切面类
// @Pointcut : 指定匹配切点集合
// @Before : 指定前置通知，value中指定切入点匹配
// @AfterReturning ：后置通知，具有可以指定返回值
// @AfterThrowing ：异常通知
//注意：前置/后置/异常通知的函数都没有返回值，只有环绕通知有返回值
@ControllerAdvice
@Component    //首先初始化切面类
@Aspect      //声明为切面类，底层使用动态代理实现AOP
public class CommonExceptionAspect {

//    JoinPoint
//    String toString();         //连接点所在位置的相关信息
//    String toShortString();     //连接点所在位置的简短相关信息
//    String toLongString();     //连接点所在位置的全部相关信息
//    Object getThis();         //返回AOP代理对象
//    Object getTarget();       //返回目标对象
//    Object[] getArgs();       //返回被通知方法参数列表
//    Signature getSignature();  //返回当前连接点签名
//    SourceLocation getSourceLocation();//返回连接点方法所在类文件中的位置
//    String getKind();        //连接点类型
//    StaticPart getStaticPart(); //返回连接点静态部分

    @AfterThrowing(value = "execution(* chaos.core.web..*.*(..))", throwing = "ex")
//    @AfterThrowing(value = "execution(* *.**.controller.*.*(..)) || execution(* com.xljt.gypsc.service.*.*(..)) && !execution(* LogModelMapper.*(..))", throwing = "ex")
    public void doAfterThrowing(JoinPoint jp, Throwable ex) throws Exception {
        ex.printStackTrace();
//        if (ex.getMessage() == null) {
//            throw new IllegalArgumentException(jp.getSignature() + ex.toString());
//        }

        if (ex instanceof DuplicateKeyException) {
            throw new IllegalArgumentException("唯一约束异常！");
        } else if (ex instanceof BadSqlGrammarException) {
            throw new IllegalArgumentException("mysql语法错误！");
        } else {
            if (ex.getMessage() == null) {
                throw new IllegalArgumentException(jp.getSignature() + ex.toString());
            }
        }
    }

    /**
     * 其他异常
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CaseRes exception(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();
        ExceptionHelper.getInstance().getExceptionService().insertExceptionModel(exception);

        String message = exception.getMessage();
        return CaseRes.getInstance().setMessage(message);
    }

}
