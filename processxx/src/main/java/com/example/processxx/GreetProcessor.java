package com.example.processxx;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Symbol;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * https://blog.csdn.net/qq_30379689/article/details/82345625
 */

@AutoService(Processor.class)
public class GreetProcessor extends AbstractProcessor {

    protected Filer filer;
    /**
     * 用来对类型进行操作的实用工具方法
     */
    protected Types types;
    /**
     * 用于注解元素的相关操作
     */
    protected Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        types = processingEnvironment.getTypeUtils();
        elements = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return false;
        }

        CodeBlock.Builder builder = CodeBlock.builder();

        Set<? extends Element> anno = roundEnvironment.getElementsAnnotatedWith(Greet.class);
        for (Element e : anno) {
            Symbol.ClassSymbol clas = (Symbol.ClassSymbol) e;
            Greet uri = clas.getAnnotation(Greet.class);

        }
        write2File(builder.build());
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Greet.class.getCanonicalName());
        return types;
    }


    private void write2File(CodeBlock block){
        MethodSpec method1 = MethodSpec.methodBuilder("methodxx1")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(TypeName.VOID)
                .addCode(block)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("MyFirstClazz")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method1)
                .build();

        try {
            JavaFile.builder("com.lxx",typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
