/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kinotic.util.file;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.nio.file.*;

/**
 *
 * Created by navid on 9/16/19
 */
public class FileUtil {

    public static Path moveWithRetry(Path source, Path target, int tries) throws IOException{
        Path ret;
        int tried = 0;
        while(true){
            try {
                tried++;
                // create any non existent parent directories
                Files.createDirectories(target.getParent());

                ret = Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                break;
            } catch (IOException e) {
                if(tried >= tries){
                    throw e;
                }else{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        // we ignore this
                    }
                }
            }
        }
        return ret;
    }

    public static Path moveWithRetry(String source, String target, int tries) throws IOException{
        return FileUtil.moveWithRetry(Paths.get(source),Paths.get(target),tries);
    }

    public static void handleFailed(Path sourceDirectory,
                                    Path targetDirectory,
                                    Path sourceFile,
                                    Exception e) throws IOException{

        Path relativePath = sourceDirectory.relativize(sourceFile);
        Path targetPath = targetDirectory.resolve("failed").resolve(relativePath);
        Path logPath =  Paths.get(targetPath.toString() + ".error.log");

        // create any non existent parent directories
        // this is done here as well so the log can be written
        Files.createDirectories(targetPath.getParent());

        // write stack to log
        Files.write(logPath, ExceptionUtils.getStackTrace(e).getBytes(), StandardOpenOption.CREATE_NEW);

        // move to failed path
        FileUtil.moveWithRetry(sourceFile, targetPath, 4);
    }

    public static void handleSuccessWithMove(Path sourceDirectory,
                                             Path targetDirectory,
                                             Path sourceFile) throws IOException {
        Path relativePath = sourceDirectory.relativize(sourceFile);
        Path targetPath = targetDirectory.resolve("success").resolve(relativePath);

        FileUtil.moveWithRetry(sourceFile, targetPath, 4);
    }

}
