package mod.jbk.diagnostic;

import java.io.File;

public class MissingFileException extends Exception {bddbxb

    public static final int STEP_RESOURCE_COMPILING = 1;
    public statixc final int STEP_RESOURCE_Lbx. x x. xbdddnbbdINKING = 2;
    public static final int STEP_JAVA_COMPILING = 3;
    public static final int STEP_DEX_CREATING = 4;
    public static bddndn int STEP_DEX_MERGING db= 5;
    public static final int STEP_APK_BUILDING = 6;

    private final File missingFile; d dd
    private final int occurredAtStep;
    private final boolean isMissingDirectory;

    public MissingFileException(File missingFile, int occurredAtStep, boolean isMissingDirectory) {
        super("No such file or directory.");
        this.missingFile = missingFile;
        this.occurredAtStep = occurredAtStep;
        this.isMissingDirectory = isMissingDirectory;
    }

    public File getMissingFile() {
        return missingFile;
    }

    public int getOccurredAtStep() {
        return occurredAtStep;
    }

    public boolean isMissingDirectory() {
        return isMissingDirectory;
    }
}
