package mod.agus.jcoderz.dex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Code {

    private final int registersSize;
    private final int insSize;
    private final int outsSize;
    private final int debugInfoOffset;
    private final short[] instructions;
    private final List<Try> tries;
    private final List<CatchHandler> catchHandlers;

    public Code(
            int registersSize,
            int insSize,
            int outsSize,
            int debugInfoOffset,
            short[] instructions,
            List<Try> tries,
            List<CatchHandler> catchHandlers) {
        this.registersSize = registersSize;
        this.insSize = insSize;
        this.outsSize = outsSize;
        this.debugInfoOffset = debugInfoOffset;
        this.instructions = Arrays.copyOf(
                Objects.requireNonNull(instructions, "instructions must not be null"),
                instructions.length
        );
        this.tries = Collections.unmodifiableList(
                Objects.requireNonNull(tries, "tries must not be null")
        );
        this.catchHandlers = Collections.unmodifiableList(
                Objects.requireNonNull(catchHandlers, "catchHandlers must not be null")
        );
    }

    public int getRegistersSize() { return registersSize; }
    public int getInsSize()       { return insSize; }
    public int getOutsSize()      { return outsSize; }
    public int getDebugInfoOffset() { return debugInfoOffset; }

    /** Returns a defensive copy of the instructions array. */
    public short[] getInstructions() {
        return Arrays.copyOf(instructions, instructions.length);
    }

    public List<Try> getTries() { return tries; }
    public List<CatchHandler> getCatchHandlers() { return catchHandlers; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code other = (Code) o;
        return registersSize   == other.registersSize
            && insSize         == other.insSize
            && outsSize        == other.outsSize
            && debugInfoOffset == other.debugInfoOffset
            && Arrays.equals(instructions, other.instructions)
            && tries.equals(other.tries)
            && catchHandlers.equals(other.catchHandlers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(registersSize, insSize, outsSize, debugInfoOffset, tries, catchHandlers);
        result = 31 * result + Arrays.hashCode(instructions);
        return result;
    }

    @Override
    public String toString() {
        return "Code{"
            + "registersSize=" + registersSize
            + ", insSize=" + insSize
            + ", outsSize=" + outsSize
            + ", debugInfoOffset=" + debugInfoOffset
            + ", instructions=" + Arrays.toString(instructions)
            + ", tries=" + tries
            + ", catchHandlers=" + catchHandlers
            + '}';
    }

    // -------------------------------------------------------------------------

    public static final class Try {

        private final int startAddress;
        private final int instructionCount;
        private final int catchHandlerIndex;

        public Try(int startAddress, int instructionCount, int catchHandlerIndex) {
            this.startAddress      = startAddress;
            this.instructionCount  = instructionCount;
            this.catchHandlerIndex = catchHandlerIndex;
        }

        public int getStartAddress()      { return startAddress; }
        public int getInstructionCount()  { return instructionCount; }

        /**
         * Returns this try's catch handler <strong>index</strong>. Note that
         * this is distinct from its catch handler <strong>offset</strong>.
         */
        public int getCatchHandlerIndex() { return catchHandlerIndex; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Try)) return false;
            Try other = (Try) o;
            return startAddress      == other.startAddress
                && instructionCount  == other.instructionCount
                && catchHandlerIndex == other.catchHandlerIndex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startAddress, instructionCount, catchHandlerIndex);
        }

        @Override
        public String toString() {
            return "Try{"
                + "startAddress=" + startAddress
                + ", instructionCount=" + instructionCount
                + ", catchHandlerIndex=" + catchHandlerIndex
                + '}';
        }
    }

    // -------------------------------------------------------------------------

    public static final class CatchHandler {

        private final int[] typeIndexes;
        private final int[] addresses;
        private final int catchAllAddress;
        private final int offset;

        public CatchHandler(int[] typeIndexes, int[] addresses, int catchAllAddress, int offset) {
            this.typeIndexes    = Arrays.copyOf(
                    Objects.requireNonNull(typeIndexes, "typeIndexes must not be null"),
                    typeIndexes.length
            );
            this.addresses      = Arrays.copyOf(
                    Objects.requireNonNull(addresses, "addresses must not be null"),
                    addresses.length
            );
            this.catchAllAddress = catchAllAddress;
            this.offset          = offset;
        }

        /** Returns a defensive copy of the type-indexes array. */
        public int[] getTypeIndexes() { return Arrays.copyOf(typeIndexes, typeIndexes.length); }

        /** Returns a defensive copy of the addresses array. */
        public int[] getAddresses()   { return Arrays.copyOf(addresses, addresses.length); }

        public int getCatchAllAddress() { return catchAllAddress; }
        public int getOffset()          { return offset; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CatchHandler)) return false;
            CatchHandler other = (CatchHandler) o;
            return catchAllAddress == other.catchAllAddress
                && offset          == other.offset
                && Arrays.equals(typeIndexes, other.typeIndexes)
                && Arrays.equals(addresses,   other.addresses);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(catchAllAddress, offset);
            result = 31 * result + Arrays.hashCode(typeIndexes);
            result = 31 * result + Arrays.hashCode(addresses);
            return result;
        }

        @Override
        public String toString() {
            return "CatchHandler{"
                + "typeIndexes=" + Arrays.toString(typeIndexes)
                + ", addresses=" + Arrays.toString(addresses)
                + ", catchAllAddress=" + catchAllAddress
                + ", offset=" + offset
                + '}';
        }
    }
}