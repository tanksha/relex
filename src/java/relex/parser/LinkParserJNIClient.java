package relex.parser;
/*
 * Copyright 2008 Novamente LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * This class serves as a wrapper to the C linkparser library.
 * Because it is using C, the class is not internally organized in a very good OOP way.
 * XXX FIXME. That is a lame excuse; nothing in C prevents good OOP style.
 *
 * XXX this class is deprecated. It only works with the old link-4.1b 
 * library, generated by the custom novamente Makefiles.
 *
 * Please use LinkParserJNINewClient with the linkparser version 4.2.6 
 * and later libraries.
 */

public class LinkParserJNIClient extends LinkParserClient {
    private static final int verbosity = 1;

    private static LinkParserJNIClient singletonInstance = null;

    private LinkParserJNIClient() {
        super();
        cTest();
    }

    static {
        // on a linux system, the actual name of the library is prefixed with
        // "lib" and suffixed with ".so" -- e.g. "liblinkparser.so"
        System.loadLibrary("linkparser");
        singletonInstance = new LinkParserJNIClient();
    }

    public static LinkParserJNIClient getSingletonInstance() {
        return singletonInstance;
    }

    // C functions for changing linkparser options
    private static native void cSetMaxParseSeconds(int maxParseSeconds);

    private static native void cSetMaxCost(int maxCost);

    // C functions in the linkparser API
    private static native void cInit(String linkparserDir);

    private static native void cTest();

    private static native void cParse(String sent);

    private static native void cClose();

    // C sentence access functions
    private static native int cNumWords();

    private static native String cGetWord(int i);

    private static native int cNumSkippedWords();

    // C linkage access functions
    private static native int cNumLinkages();

    private static native void cMakeLinkage(int index);

    private static native int cLinkageNumViolations();

    private static native int cLinkageDisjunctCost();

    private static native int cNumLinks();

    private static native int cLinkLWord(int link);

    private static native int cLinkRWord(int link);

    private static native String cLinkLLabel(int link);

    private static native String cLinkRLabel(int link);

    private static native String cLinkLabel(int link);

    private static native String cConstituentString();

    private static native String cLinkString();

    // OTHER UTILITY C FUNCTIONS
    private static native boolean cIsPastTenseForm(String word);

    private static native boolean cIsEntity(String word);

    public void setMaxParseSeconds(int maxParseSeconds) {
        super.setMaxParseSeconds(maxParseSeconds);
        cSetMaxParseSeconds(maxParseSeconds);
    }

    public void setMaxCost(int maxCost) {
        super.setMaxCost(maxCost);
        cSetMaxCost(maxCost);
    }

    public boolean isPastTenseForm(String word) {
        return cIsPastTenseForm(word);
    }

    public boolean isEntity(String word) {
        return cIsEntity(word);
    }

    public void close() {
        cClose();
    }

    public void init(String pathname) {
    	super.init(pathname);
        if (verbosity > 3) System.out.println("LinkParserJNIClient: initializing with:"+ pathname);
        cInit(pathname);
    }

    void execParse(String sentence) {
        if (verbosity > 3) System.out.println("parsing:" + sentence + "[end_sentence]");
        cParse(sentence);
        if (verbosity > 3) System.out.println("parsing completed.");
    }

    int getNumLinkages() {
        return cNumLinkages();
    }

    void makeLinkage(int i) {
        cMakeLinkage(i);
    }

    String getConstituentString() {
        return cConstituentString();
    }

    int getNumSkippedWords() {
        return cNumSkippedWords();
    }

    int getNumWords() {
        return cNumWords();
    }

    String getWord(int w) {
        return cGetWord(w);
    }

    int getLinkageDisjunctCost() {
        return cLinkageDisjunctCost();
    }

    int getLinkageNumViolations() {
        return cLinkageNumViolations();
    }

    int getNumLinks() {
        return cNumLinks();
    }

    String getLinkString() {
        return cLinkString();
    }

    int getLinkLWord(int i) {
        return cLinkLWord(i);
    }

    int getLinkRWord(int i) {
        return cLinkRWord(i);
    }

    String getLinkLabel(int i) {
        return cLinkLabel(i);
    }

    String getLinkLLabel(int i) {
        return cLinkLLabel(i);
    }

    String getLinkRLabel(int i) {
        return cLinkRLabel(i);
    }
}


