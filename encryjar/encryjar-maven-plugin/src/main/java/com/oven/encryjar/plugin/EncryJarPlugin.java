package com.oven.encryjar.plugin;

import com.oven.encryjar.Const;
import com.oven.encryjar.JarEncryptor;
import com.oven.encryjar.util.StrUtils;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;

/**
 * 加密jar/war文件的maven插件
 *
 * @author Oven
 */
@SuppressWarnings("unused")
@Mojo(name = "encryjar", defaultPhase = LifecyclePhase.PACKAGE)
public class EncryJarPlugin extends AbstractMojo {

    // MavenProject
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;
    // 密码
    @Parameter(required = true)
    private String password;
    // 机器码
    @Parameter
    private String code;
    // 加密的内部-lib/jar名称
    @Parameter
    private String libjars;
    // 要加密的包名前缀
    @Parameter
    private String packages;
    // 要加密的配置文件名
    @Parameter
    private String cfgfiles;
    // 排除的类名
    @Parameter
    private String excludes;
    // 外部依赖jarlib
    @Parameter
    private String classpath;
    // 调试
    @Parameter(defaultValue = "false")
    private Boolean debug;

    /**
     * 打包的时候执行
     */
    public void execute() {
        Const.DEBUG = debug;
        Build build = project.getBuild();

        String targetJar = build.getDirectory() + File.separator + build.getFinalName() + "." + project.getPackaging();
        List<String> includeJarList = StrUtils.toList(libjars);
        List<String> packageList = StrUtils.toList(packages);
        List<String> excludeClassList = StrUtils.toList(excludes);
        List<String> classPathList = StrUtils.toList(classpath);
        List<String> cfgFileList = StrUtils.toList(cfgfiles);
        includeJarList.add("-");

        JarEncryptor encryptor = new JarEncryptor(targetJar, password.trim().toCharArray());
        encryptor.setCode(StrUtils.isEmpty(code) ? null : code.trim().toCharArray());
        encryptor.setPackages(packageList);
        encryptor.setIncludeJars(includeJarList);
        encryptor.setExcludeClass(excludeClassList);
        encryptor.setClassPath(classPathList);
        encryptor.setCfgfiles(cfgFileList);
        String result = encryptor.doEncryptJar();
    }

}