
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Karthik_StringSearching_JUnit {
    private static final int TIMEOUT = 200;


    private CharacterComparator comparator;

    private List<Integer> emptyList;

    @Before
    public void setUp() {
        emptyList = new ArrayList<Integer>();
        comparator = new CharacterComparator();
    }

    ////////////////////////////////////
    //Brute Force Tests

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bruteForceNullPattern() {
        String text = "Roses are red, violets are blue. I love CS 1332 and so"
                + " do you.";
        PatternMatching.bruteForce(null, text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bruteForceEmptyPattern() {
        String pattern = "";
        String text = "Roses are red, violets are blue. I love CS 1332 and so"
                + " do you.";
        PatternMatching.bruteForce(pattern, text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bruteForceNullText() {
        String pattern = "abasdvsafasf";
        PatternMatching.bruteForce(pattern, null, comparator);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void bruteForceNullComparator() {
        comparator = null;
        String pattern = "a";
        String text = "abcdefg";
        PatternMatching.bruteForce(pattern, text, comparator);
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceLongerPatternThanText() {
        String pattern = "asdfasdfasdfasdfasdf";
        String text = "asdfafadsf";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceNoMatch() {
        String pattern = "COVID19";
        String text = "COVID-19 is classified as a pandemic based on its "
                + "geographic spread.";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertTrue("You did use the comparator beacuse your comparison count "
                + "was " + comparator.getComparisonCount(),
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 67.", 67, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForcePatternLengthEqualsTextLengthMatch() {
        //Well known Spanish song by Juanes. A snippet of the song lyrics!
        //URL: https://www.azlyrics.com/lyrics/juanes/adislepido.html
        String text = "Que mis ojos se despierten con la luz de tu mirada, yo\n"
                + "A Dios le pido\n"
                + "Que mi madre no se muera y que mi padre me recuerde\n"
                + "A Dios le pido\n"
                + "Que te quedes a mi lado y que más nunca te me vayas, mi "
                + "vida\n"
                + "A Dios le pido";
        String pattern = text;
        List<Integer> expectedMatches = new ArrayList<Integer>();
        expectedMatches.add(0);
        assertEquals(expectedMatches, PatternMatching.bruteForce(pattern,
                text, comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 212.", 212, comparator.getComparisonCount());
        //212 is the length of the text
    }

    @Test(timeout = TIMEOUT)
    public void bruteForcePatternLengthEqualsTextLengthNoMatch() {
        //Well known Spanish song by Juanes. A snippet of the song lyrics!
        //URL: https://www.azlyrics.com/lyrics/juanes/adislepido.html
        String text = "Que mis ojos se despierten con la luz de tu mirada, yo\n"
                + "A Dios le pido\n"
                + "Que mi madre no se muera y que mi padre me recuerde\n"
                + "A Dios le pido\n"
                + "Que te quedes a mi lado y que más nunca te me vayas, mi "
                + "vida\n"
                + "A Dios le pido";
        String pattern = "Que mi ojos se despierten con la luz de tu mirada, "
                + "yo\n"
                + "A Dios le pido\n"
                + "Que mi madre no se muera y que mi padre me recuerde\n"
                + "A Dios le pido\n"
                + "Que te quedes a mi lado y que más nunca te me vayas, mi "
                + "vida\n"
                + "A Dios le pido";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 8.", 8, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceTextLengthEqualsPatternLengthNoMatchWorstCase() {
        //Well known Spanish song by Juanes. A snippet of the song lyrics!
        //URL: https://www.azlyrics.com/lyrics/juanes/adislepido.html
        String text = "Que mis ojos se despierten con la luz de tu mirada, yo\n"
                + "A Dios le pido\n"
                + "Que mi madre no se muera y que mi padre me recuerde\n"
                + "A Dios le pido\n"
                + "Que te quedes a mi lado y que más nunca te me vayas, mi "
                + "vida\n"
                + "A Dios le pido";
        String pattern = "Que mis ojos se despierten con la luz de tu mirada,"
                + " yo\n"
                + "A Dios le pido\n"
                + "Que mi madre no se muera y que mi padre me recuerde\n"
                + "A Dios le pido\n"
                + "Que te quedes a mi lado y que más nunca te me vayas, mi "
                + "vida\n"
                + "A Dios le pida";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 212.", 212, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForcePatternReverseOfText() {
        String text = "I will be going to San Fransisco for vacation!";
        String pattern = "!noitacav rof ocsisnarF naS ot gniog eb lliw I";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 1.", 1, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForcePalindrome() {
        String text = "Go Hang A Salami!";
        String pattern = "I'm A Lasagna Hog";
        assertEquals(emptyList, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 1.", 1, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForcePalindromeOneWord() {
        String text = "tattarrattat";
        String pattern = "tattarrattat"; //longest palindrome in the English
        // Language!
        List<Integer> matchesExpected = new ArrayList<Integer>();
        matchesExpected.add(0);
        assertEquals(matchesExpected, PatternMatching.bruteForce(pattern,
                text, comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 12.", 12, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceMatchIsAtEnd() {
        String text = "I love to drink hot chocolate";
        String pattern = "chocolate";
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(20);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 29.", 29, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceBestCase() {
        String text = "Give me some sunshine, Gimme me some rain!";
        String pattern = "Give";
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 44.", 44, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void bruteForceMatchInMiddle() {
        String text = "She sells seashells down by the seashore";
        String pattern = "seashells";
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 45.", 45, comparator.getComparisonCount());
    }

    //////////////////////////////////////////////////////
    //building failure table

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void buildFailureTableNullPattern() {
        PatternMatching.buildFailureTable(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void buildFailureTableNullComparator() {
        String pattern = "asdfadsf";
        PatternMatching.buildFailureTable(pattern, null);
    }

    @Test(timeout = TIMEOUT)
    public void buildFailureTableEmptyPattern() {
        String pattern = "";
        int[] expected = new int[pattern.length()];
        assertArrayEquals(expected, PatternMatching.buildFailureTable(pattern,
                comparator));
        assertTrue("Did use the comparator.",
                comparator.getComparisonCount() == 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable() {
        /*
            pattern: ababa
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
         */
        int[] failureTable = PatternMatching.buildFailureTable("ababa",
                comparator);
        int[] expected = {0, 0, 1, 2, 3};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void buildFailTableAllZeroes() {
        String pattern = "abcdefghijklmnopqrstuvwxyz";
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] actual = PatternMatching.buildFailureTable(pattern, comparator);
        assertArrayEquals(expected, actual);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 25.", 25, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void buildFailTableLotsOfMatches() {
        String pattern = "aaaaaaaaaa";
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] actual = PatternMatching.buildFailureTable(pattern, comparator);
        assertArrayEquals(expected, actual);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void buildFailureTableNormal() {
        String pattern = "ababc";
        int[] expected = {0,0,1,2,0};
        int[] actual = PatternMatching.buildFailureTable(pattern, comparator);
        assertArrayEquals(expected, actual);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 5.", 5, comparator.getComparisonCount());
    }
    ////////////////////////////////////////////
    //kmp tests

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void kmpNullPattern() {
        String text = "I love chocolate";
        PatternMatching.kmp(null, text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void kmpZeroLengthPattern() {
        String text = "I love chocolate";
        PatternMatching.kmp("", text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void kmpNullText() {
        String pattern = "asdfadsf";
        PatternMatching.kmp(pattern, null, comparator);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void kmpNullComparator() {
        String text = "Love is a photograph";
        String pattern = "photo";
        PatternMatching.kmp(pattern, text, null);
    }

    @Test(timeout = TIMEOUT)
    public void kmpZeroLengthText() {
        String pattern = "i love";
        List<Integer> expected = new ArrayList<Integer>();
        assertEquals(expected, PatternMatching.kmp(pattern, "",
                comparator));

        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void kmpPatternLengthEqualTextLengthMatch() {
        //Well known song lyric!
        //Source: LyricFind
        String pattern = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        String text = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 197.", 197, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void kmpPatternLengthEqualTextLengthNoMatch() {
        String pattern = "Tommy used to work on the socks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        String text = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 125.", 125, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void patternReverseOfText() {
        //Well known poem by Edgar Allan Poe. Poem is called "A Dream Within
        // a Dream"
        //Source: https://www.poetryfoundation.org/poems/52829/a-dream-within-a-dream
        String pattern = "?enog ssel eht erofereht ti sI\n"
                + ",enon ni ro ,noisiv a nI\n"
                + ",yad a ni ro ,thgin a nI\n"
                + "yawa nwolf sah epoh fi teY\n"
                + ";maerd a neeb evah syad ym tahT";
        String text = "That my days have been a dream;\n"
                + "Yet if hope has flown away\n"
                + "In a night, or in a day,\n"
                + "In a vision, or in none,\n"
                + "Is it therefore the less gone?";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 139.", 139, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void kmpSmallTextOneMatch() {
        String pattern = "ab";
        String text = "abcd";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 4.", 4, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void kmpOneMatch() {
        //Poem: Ode to a Nightingale
        //Source: https://www.poetryfoundation.org/poems/44479/ode-to-a-nightingale
        String pattern = "draught";
        String text = "O, for a draught of vintage! that hath been\n"
                + "         Cool'd a long age in the deep-delved earth,\n"
                + "Tasting of Flora and the country green,\n"
                + "         Dance, and Provençal song, and sunburnt mirth!";
        List<Integer> expected = new ArrayList<>();
        expected.add(9);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 199.", 199, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void kmpOneMatchMiddle() {
        String text = "I love hot chocolate";
        String pattern = "love";
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 21.", 21, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void kmpOneMatchEnd() {
        String text = "Hola! Me llamo Paul.";
        String pattern = "Paul.";
        List<Integer> expected = new ArrayList<>();
        expected.add(15);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 24.", 24, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void kmpMultipleMatch() {
        //Movie Line from Sean Mcguire (Good will Hunting)
        //Source: https://everydaypower.com/famous-movie-quotes/
        String text = "Some people can’t believe in themselves until someone else believes in them first.";
        String pattern = "believe";
        List<Integer> expected = new ArrayList<>();
        expected.add(18);
        expected.add(59);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 82.", 82, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void riddleAnswerKMP() {
        //Popular riddle
        String text = "What comes twice in a week, once in a year and never "
                + "in a month";
        String riddleAnswer = "e";
        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(15);
        expected.add(23);
        expected.add(24);
        expected.add(31);
        expected.add(39);
        expected.add(48);
        expected.add(50);
        assertEquals(expected, PatternMatching.kmp(riddleAnswer, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 63.", 63, comparator.getComparisonCount());
    }
    /////////////////////////////////////////
    //build last occurrence table for Boyer-Moore
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void buildLastTableNullPattern() {
        PatternMatching.buildLastTable(null);
    }
    @Test(timeout = TIMEOUT)
    public void buildLastTableEmptyPattern() {
        assertEquals(new Integer(-1),
                PatternMatching.buildLastTable("").getOrDefault("A",
         -1));
        assertEquals(new Integer(-1),
                PatternMatching.buildLastTable("").getOrDefault("B",
                        -1));
        assertTrue("Did use the comparator.",
                comparator.getComparisonCount() == 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void buildLastTableNormal() {
        String pattern = "sell";
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('s', 0);
        expectedLastTable.put('e', 1);
        expectedLastTable.put('l', 3);
        assertEquals(expectedLastTable, lastTable);
        assertTrue("Did use the comparator.",
                comparator.getComparisonCount() == 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void buildLastTableAllSameLetter() {
        String pattern = "aaaaaaaaaa";
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', pattern.length() - 1);
        assertEquals(expected, lastTable);
        assertTrue("Did use the comparator.",
                comparator.getComparisonCount() == 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void buildAlphabetTable() {
        String pattern = "abcdefghijklmnopqrstuvwxyz";
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', 0);
        expected.put('b', 1);
        expected.put('c', 2);
        expected.put('d', 3);
        expected.put('e', 4);
        expected.put('f', 5);
        expected.put('g', 6);
        expected.put('h', 7);
        expected.put('i', 8);
        expected.put('j', 9);
        expected.put('k', 10);
        expected.put('l', 11);
        expected.put('m', 12);
        expected.put('n', 13);
        expected.put('o', 14);
        expected.put('p', 15);
        expected.put('q', 16);
        expected.put('r', 17);
        expected.put('s', 18);
        expected.put('t', 19);
        expected.put('u', 20);
        expected.put('v', 21);
        expected.put('w', 22);
        expected.put('x', 23);
        expected.put('y', 24);
        expected.put('z', 25);
        assertEquals(expected, lastTable);
        assertTrue("Did use the comparator.",
                comparator.getComparisonCount() == 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }
    //////////////////////////////////////////////////////////////////////////
    //Boyer-Moore Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BoyerMooreNullPattern() {
        String text = "I love chocolate";
        PatternMatching.boyerMoore(null, text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BoyerMooreZeroLengthPattern() {
        String text = "I love chocolate";
        PatternMatching.boyerMoore("", text, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BoyerMooreNullText() {
        String pattern = "asdfadsf";
        PatternMatching.boyerMoore(pattern, null, comparator);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void BoyerMooreNullComparator() {
        String text = "Love is a photograph";
        String pattern = "photo";
        PatternMatching.boyerMoore(pattern, text, null);
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMooreZeroLengthText() {
        String pattern = "i love";
        List<Integer> expected = new ArrayList<Integer>();
        assertEquals(expected, PatternMatching.boyerMoore(pattern, "",
                comparator));

        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMoorePatternLengthEqualTextLengthMatch() {
        //Well known song lyric!
        //Source: LyricFind
        String pattern = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        String text = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 99.", 99, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMoorePatternLengthEqualTextLengthNoMatch() {
        String pattern = "Tommy used to work on the socks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        String text = "Tommy used to work on the docks, union's been on "
                + "strike\n"
                + "He's down on his luck, it's tough, so tough";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 73.", 73, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMoorepatternReverseOfText() {
        //Well known poem by Edgar Allan Poe. Poem is called "A Dream Within
        // a Dream"
        //Source: https://www.poetryfoundation.org/poems/52829/a-dream-within-a-dream
        String pattern = "?enog ssel eht erofereht ti sI\n"
                + ",enon ni ro ,noisiv a nI\n"
                + ",yad a ni ro ,thgin a nI\n"
                + "yawa nwolf sah epoh fi teY\n"
                + ";maerd a neeb evah syad ym tahT";
        String text = "That my days have been a dream;\n"
                + "Yet if hope has flown away\n"
                + "In a night, or in a day,\n"
                + "In a vision, or in none,\n"
                + "Is it therefore the less gone?";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 1.", 1, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void BoyerMooreSmallTextOneMatch() {
        String pattern = "ab";
        String text = "abcd";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 3.", 3, comparator.getComparisonCount());
    }
    @Test(timeout = TIMEOUT)
    public void BoyerMooreOneMatch() {
        //Poem: Ode to a Nightingale
        //Source: https://www.poetryfoundation.org/poems/44479/ode-to-a-nightingale
        String pattern = "draught";
        String text = "O, for a draught of vintage! that hath been\n"
                + "         Cool'd a long age in the deep-delved earth,\n"
                + "Tasting of Flora and the country green,\n"
                + "         Dance, and Provençal song, and sunburnt mirth!";
        List<Integer> expected = new ArrayList<>();
        expected.add(9);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 42.", 42, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMooreOneMatchMiddle() {
        String text = "I love hot chocolate";
        String pattern = "love";
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMooreOneMatchEnd() {
        String text = "Hola! Me llamo Paul.";
        String pattern = "Paul.";
        List<Integer> expected = new ArrayList<>();
        expected.add(15);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 10.", 10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void BoyerMooreMultipleMatch() {
        //Movie Line from Sean Mcguire (Good will Hunting)
        //Source: https://everydaypower.com/famous-movie-quotes/
        String text = "Some people can’t believe in themselves until someone else believes in them first.";
        String pattern = "believe";
        List<Integer> expected = new ArrayList<>();
        expected.add(18);
        expected.add(59);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 30.", 30, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void riddleAnswerBoyerMoore() {
        //Popular riddle
        String text = "What comes twice in a week, once in a year and never "
                + "in a month";
        String riddleAnswer = "e";
        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(15);
        expected.add(23);
        expected.add(24);
        expected.add(31);
        expected.add(39);
        expected.add(48);
        expected.add(50);
        assertEquals(expected, PatternMatching.boyerMoore(riddleAnswer, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 63.", 63, comparator.getComparisonCount());
    }
    ///////////////////////////////////////////////////////
    //Fun tests

    @Test(timeout = TIMEOUT)
    public void stringSearchRawCSVFile() {
        //Raw CSV File
        //Source: http://datapipes.okfnlabs.org/csv/?url=https://raw.github.com/okfn/datapipes/master/test/data/gla.csv
        String text = ",Report of all payments made by GLA & GLA Land & "
                + "Property for value equal to or greater than � 250.00 Excl."
                + " VAT,,,,,,\n"
                + ",Reporting Period : ,12,,,,,\n"
                + ",Start Date:,\"3rd February, 2013\",,,,,\n"
                + ",End Date:,\"2nd March, 2013\",,,,,\n"
                + ",Financial Year :,2012 / 13,,,,,\n"
                + ",,,,,,,\n"
                + ",Vendor ID,Vendor Name,Cost Element,Expenditure Account "
                + "Code Description,Document No,Amount,Clearing Date\n"
                + ",18000063,CIRCLE ANGLIA LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017038,\"4,007,633.00\",06 Feb  "
                + "2013\n"
                + ",18000050,ONE HOUSING GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017214,\"3,047,294.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017060,\"2,850,000.00\",07 Feb  "
                + "2013\n"
                + ",18000078,BERKELEY PARTNERSHIP HOMES LIMITED,544078,HSG "
                + "Grants to Non-Registered Providers,1900017134,\"2,453,235"
                + ".00\",13 Feb  2013\n"
                + ",15500097,LONDON BOROUGH OF CAMDEN,544075,Grants to "
                + "External Organisations,5107795327,\"1,829,793.00\",14 Feb "
                + " 2013\n"
                + ",18000092,WANDSWORTH COUNCIL,544077,HSG Grants to Local "
                + "Authorities,1900017174,\"1,814,500.00\",20 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017201,\"1,597,410.00\",27 Feb  "
                + "2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017178,\"1,584,000.00\",22 Feb"
                + "  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017200,\"1,479,161.00\",27 Feb  "
                + "2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107785735,\"1,300,000.00\",13 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017062,\"1,278,125.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017037,\"1,113,030.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017025,\"1,035,334.00\",05 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017070,\"962,564.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017034,\"816,497.00\",06 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800156,\"743,130.35\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017073,\"709,088.00\",07 Feb "
                + " 2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017075,\"667,500.00\",08 Feb  "
                + "2013\n"
                + ",18000045,LONDON AND QUADRANT HOUSING TRUST,544076,HSG "
                + "Grants to Registered Providers,1900017027,\"617,342.00\","
                + "05 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017177,\"528,000.00\",22 Feb  "
                + "2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542300,Legal Fees,"
                + "5107780290,\"525,000.00\",20 Feb  2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017046,\"472,500.00\",06 Feb  "
                + "2013\n"
                + ",10002550,CITY OF WESTMINSTER,544075,Grants to External "
                + "Organisations,5107779502,\"423,560.00\",19 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017076,\"408,000.00\",07 Feb  "
                + "2013\n"
                + ",18000031,ORBIT HOUSING GROUP LTD,544076,HSG Grants to "
                + "Registered Providers,1900017071,\"367,328.00\",07 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017068,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017069,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107795798,\"349,573.20\",14 Feb  2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542320,Shared Services "
                + "Fees,5107786290,\"323,954.10\",12 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017171,\"312,183.00\",20 Feb  "
                + "2013\n"
                + ",18000051,VIRIDIAN HOUSING,544076,HSG Grants to Registered"
                + " Providers,1900017173,\"307,500.00\",20 Feb  2013\n"
                + ",10022339,TFL GROUP PROPERTY,510051,LAND/GROUND RENTS,"
                + "5107788855,\"300,854.40\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017065,\"293,094.00\",07 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017072,\"282,364.00\",07 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775201,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775210,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107778828,\"245,866.25\",12 Feb  2013\n"
                + ",18000066,EAST THAMES GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017077,\"240,582.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017101,\"234,137.00\",08 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017067,\"225,000.00\","
                + "07 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768186,\"211,670.60\",19 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017064,\"211,309.00\",07 Feb  "
                + "2013\n"
                + ",10002766,LONDON COUNCILS,550035,CCS Adjudication Services"
                + " Contract,5107799626,\"205,152.26\",22 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017102,\"203,312.00\",08 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017024,\"188,243.00\",05 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017057,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017066,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",10000044,CITY OF LONDON,544075,Grants to External "
                + "Organisations,5107772234,\"175,725.00\",06 Feb  2013\n"
                + ",10020714,BRITISH FASHION COUNCIL,544075,Grants to "
                + "External Organisations,5107785702,\"174,678.22\",06 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017103,\"174,267.00\",08 Feb  "
                + "2013\n"
                + ",10013679,SAFER LONDON FOUNDATION,544075,Grants to "
                + "External Organisations,5107788674,\"173,685.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017032,\"169,418.00\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017056,\"162,830.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017142,\"156,092.00\",14 Feb  "
                + "2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017047,\"145,098.00\",06 Feb  "
                + "2013\n"
                + ",10007104,LONDON BOROUGH OF ENFIELD,544075,Grants to "
                + "External Organisations,5107804988,\"143,440.61\",26 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800155,\"136,526.07\",20 Feb  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017030,\"133,118.00\",06 Feb  "
                + "2013\n"
                + ",10021258,SEETEC,544075,Grants to External Organisations,"
                + "5107790660,\"128,493.73\",25 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017031,\"126,309.00\","
                + "06 Feb  2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107802075,\"116,195.93\",25 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017172,\"116,178.00\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017033,\"115,500.00\",06 Feb "
                + " 2013\n"
                + ",10021972,ROOFF LIMITED,550010,Contracted Services "
                + "Buildings,5107796654,\"114,740.00\",18 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900016988,\"113,305.00\","
                + "04 Feb  2013\n"
                + ",18000121,ELDON HOUSING ASSOCIATION LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017085,\"109,890.00\","
                + "07 Feb  2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017074,\"105,000.00\",08 Feb  "
                + "2013\n"
                + ",15050001,HMRC,544070,MISCELLANEOUS FINANCE EXPENSES,"
                + "1900000632,\"102,537.00\",26 Feb  2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800887,\"101,888.90\",21 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017141,\"101,656.00\",14 Feb  "
                + "2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107789866,\"99,490.75\",07 Feb  2013\n"
                + ",10014839,LOCOG,544075,Grants to External Organisations,"
                + "5107737034,\"95,000.00\",14 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768150,\"90,715.97\",19 Feb  2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017036,\"83,477.00\",06 Feb  2013\n"
                + ",10020632,ST MUNGOS,544075,Grants to External "
                + "Organisations,5107769950,\"79,000.00\",06 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017058,\"78,046.00\",07 Feb  2013\n"
                + ",10008276,LPFA,501000,Pension Augmentation,1900017143,"
                + "\"77,922.97\",14 Feb  2013\n"
                + ",10014278,R.B KENSINGTON & CHELSEA,544075,Grants to "
                + "External Organisations,5107777970,\"74,069.00\",13 Feb  "
                + "2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800893,\"70,195.60\",21 Feb  2013\n"
                + ",10018194,AMATEUR SWIMMING ASSOCIATION,544075,Grants to "
                + "External Organisations,5107779983,\"70,000.00\",28 Feb  "
                + "2013\n"
                + ",10018127,GARDINER & THEOBALD FAIRWAY LTD,542000,"
                + "Management & Support Consultancy,5107786283,\"68,500.25\","
                + "08 Feb  2013\n"
                + ",10018379,JACOBS U.K LIMITED,542000,Management & Support "
                + "Consultancy,5107768420,\"67,563.75\",05 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017061,\"66,559.00\",07 Feb  2013\n"
                + ",10022226,DEPARTMENT OF HEALTH,544075,Grants to External "
                + "Organisations,5107788401,\"65,900.83\",26 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107788928,\"65,726.00\",06 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017063,\"62,161.00\",07 Feb  2013\n"
                + ",10018467,SOUND & LIGHT PRODUCTIONS,540110,Marketing "
                + "Exhibitions & Events,5107804862,\"58,000.00\",01 Mar  2013\n"
                + ",10001984,TURNER & TOWNSEND PROJECT MAGAG,542000,"
                + "Management & Support Consultancy,5107788132,\"53,299.35\","
                + "26 Feb  2013\n"
                + ",10006953,LONDON BOROUGH OF REDBRIDGE,544075,Grants to "
                + "External Organisations,5107806343,\"51,422.31\",28 Feb  "
                + "2013\n"
                + ",10010396,ACCENTURE EUROPEAN SERVICE CENTRE,544075,Grants "
                + "to External Organisations,5107785746,\"50,000.00\",04 Feb "
                + " 2013\n"
                + ",15500400,LONDON BOROUGH OF SOUTHWARK,544075,Grants to "
                + "External Organisations,5107807567,\"50,000.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017055,\"50,000.00\",07 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107730369,\"49,054.00\",06 Feb "
                + " 2013";
        String pattern = "London";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 1880.", 1880, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void stringSearchRawCSVForCityBoyerMoore() {
        //Raw CSV File
        //Source: http://datapipes.okfnlabs.org/csv/?url=https://raw.github.com/okfn/datapipes/master/test/data/gla.csv
        String text = ",Report of all payments made by GLA & GLA Land & "
                + "Property for value equal to or greater than � 250.00 Excl."
                + " VAT,,,,,,\n"
                + ",Reporting Period : ,12,,,,,\n"
                + ",Start Date:,\"3rd February, 2013\",,,,,\n"
                + ",End Date:,\"2nd March, 2013\",,,,,\n"
                + ",Financial Year :,2012 / 13,,,,,\n"
                + ",,,,,,,\n"
                + ",Vendor ID,Vendor Name,Cost Element,Expenditure Account "
                + "Code Description,Document No,Amount,Clearing Date\n"
                + ",18000063,CIRCLE ANGLIA LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017038,\"4,007,633.00\",06 Feb  "
                + "2013\n"
                + ",18000050,ONE HOUSING GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017214,\"3,047,294.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017060,\"2,850,000.00\",07 Feb  "
                + "2013\n"
                + ",18000078,BERKELEY PARTNERSHIP HOMES LIMITED,544078,HSG "
                + "Grants to Non-Registered Providers,1900017134,\"2,453,235"
                + ".00\",13 Feb  2013\n"
                + ",15500097,LONDON BOROUGH OF CAMDEN,544075,Grants to "
                + "External Organisations,5107795327,\"1,829,793.00\",14 Feb "
                + " 2013\n"
                + ",18000092,WANDSWORTH COUNCIL,544077,HSG Grants to Local "
                + "Authorities,1900017174,\"1,814,500.00\",20 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017201,\"1,597,410.00\",27 Feb  "
                + "2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017178,\"1,584,000.00\",22 Feb"
                + "  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017200,\"1,479,161.00\",27 Feb  "
                + "2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107785735,\"1,300,000.00\",13 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017062,\"1,278,125.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017037,\"1,113,030.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017025,\"1,035,334.00\",05 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017070,\"962,564.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017034,\"816,497.00\",06 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800156,\"743,130.35\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017073,\"709,088.00\",07 Feb "
                + " 2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017075,\"667,500.00\",08 Feb  "
                + "2013\n"
                + ",18000045,LONDON AND QUADRANT HOUSING TRUST,544076,HSG "
                + "Grants to Registered Providers,1900017027,\"617,342.00\","
                + "05 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017177,\"528,000.00\",22 Feb  "
                + "2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542300,Legal Fees,"
                + "5107780290,\"525,000.00\",20 Feb  2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017046,\"472,500.00\",06 Feb  "
                + "2013\n"
                + ",10002550,CITY OF WESTMINSTER,544075,Grants to External "
                + "Organisations,5107779502,\"423,560.00\",19 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017076,\"408,000.00\",07 Feb  "
                + "2013\n"
                + ",18000031,ORBIT HOUSING GROUP LTD,544076,HSG Grants to "
                + "Registered Providers,1900017071,\"367,328.00\",07 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017068,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017069,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107795798,\"349,573.20\",14 Feb  2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542320,Shared Services "
                + "Fees,5107786290,\"323,954.10\",12 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017171,\"312,183.00\",20 Feb  "
                + "2013\n"
                + ",18000051,VIRIDIAN HOUSING,544076,HSG Grants to Registered"
                + " Providers,1900017173,\"307,500.00\",20 Feb  2013\n"
                + ",10022339,TFL GROUP PROPERTY,510051,LAND/GROUND RENTS,"
                + "5107788855,\"300,854.40\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017065,\"293,094.00\",07 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017072,\"282,364.00\",07 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775201,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775210,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107778828,\"245,866.25\",12 Feb  2013\n"
                + ",18000066,EAST THAMES GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017077,\"240,582.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017101,\"234,137.00\",08 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017067,\"225,000.00\","
                + "07 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768186,\"211,670.60\",19 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017064,\"211,309.00\",07 Feb  "
                + "2013\n"
                + ",10002766,LONDON COUNCILS,550035,CCS Adjudication Services"
                + " Contract,5107799626,\"205,152.26\",22 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017102,\"203,312.00\",08 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017024,\"188,243.00\",05 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017057,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017066,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",10000044,CITY OF LONDON,544075,Grants to External "
                + "Organisations,5107772234,\"175,725.00\",06 Feb  2013\n"
                + ",10020714,BRITISH FASHION COUNCIL,544075,Grants to "
                + "External Organisations,5107785702,\"174,678.22\",06 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017103,\"174,267.00\",08 Feb  "
                + "2013\n"
                + ",10013679,SAFER LONDON FOUNDATION,544075,Grants to "
                + "External Organisations,5107788674,\"173,685.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017032,\"169,418.00\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017056,\"162,830.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017142,\"156,092.00\",14 Feb  "
                + "2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017047,\"145,098.00\",06 Feb  "
                + "2013\n"
                + ",10007104,LONDON BOROUGH OF ENFIELD,544075,Grants to "
                + "External Organisations,5107804988,\"143,440.61\",26 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800155,\"136,526.07\",20 Feb  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017030,\"133,118.00\",06 Feb  "
                + "2013\n"
                + ",10021258,SEETEC,544075,Grants to External Organisations,"
                + "5107790660,\"128,493.73\",25 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017031,\"126,309.00\","
                + "06 Feb  2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107802075,\"116,195.93\",25 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017172,\"116,178.00\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017033,\"115,500.00\",06 Feb "
                + " 2013\n"
                + ",10021972,ROOFF LIMITED,550010,Contracted Services "
                + "Buildings,5107796654,\"114,740.00\",18 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900016988,\"113,305.00\","
                + "04 Feb  2013\n"
                + ",18000121,ELDON HOUSING ASSOCIATION LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017085,\"109,890.00\","
                + "07 Feb  2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017074,\"105,000.00\",08 Feb  "
                + "2013\n"
                + ",15050001,HMRC,544070,MISCELLANEOUS FINANCE EXPENSES,"
                + "1900000632,\"102,537.00\",26 Feb  2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800887,\"101,888.90\",21 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017141,\"101,656.00\",14 Feb  "
                + "2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107789866,\"99,490.75\",07 Feb  2013\n"
                + ",10014839,LOCOG,544075,Grants to External Organisations,"
                + "5107737034,\"95,000.00\",14 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768150,\"90,715.97\",19 Feb  2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017036,\"83,477.00\",06 Feb  2013\n"
                + ",10020632,ST MUNGOS,544075,Grants to External "
                + "Organisations,5107769950,\"79,000.00\",06 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017058,\"78,046.00\",07 Feb  2013\n"
                + ",10008276,LPFA,501000,Pension Augmentation,1900017143,"
                + "\"77,922.97\",14 Feb  2013\n"
                + ",10014278,R.B KENSINGTON & CHELSEA,544075,Grants to "
                + "External Organisations,5107777970,\"74,069.00\",13 Feb  "
                + "2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800893,\"70,195.60\",21 Feb  2013\n"
                + ",10018194,AMATEUR SWIMMING ASSOCIATION,544075,Grants to "
                + "External Organisations,5107779983,\"70,000.00\",28 Feb  "
                + "2013\n"
                + ",10018127,GARDINER & THEOBALD FAIRWAY LTD,542000,"
                + "Management & Support Consultancy,5107786283,\"68,500.25\","
                + "08 Feb  2013\n"
                + ",10018379,JACOBS U.K LIMITED,542000,Management & Support "
                + "Consultancy,5107768420,\"67,563.75\",05 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017061,\"66,559.00\",07 Feb  2013\n"
                + ",10022226,DEPARTMENT OF HEALTH,544075,Grants to External "
                + "Organisations,5107788401,\"65,900.83\",26 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107788928,\"65,726.00\",06 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017063,\"62,161.00\",07 Feb  2013\n"
                + ",10018467,SOUND & LIGHT PRODUCTIONS,540110,Marketing "
                + "Exhibitions & Events,5107804862,\"58,000.00\",01 Mar  2013\n"
                + ",10001984,TURNER & TOWNSEND PROJECT MAGAG,542000,"
                + "Management & Support Consultancy,5107788132,\"53,299.35\","
                + "26 Feb  2013\n"
                + ",10006953,LONDON BOROUGH OF REDBRIDGE,544075,Grants to "
                + "External Organisations,5107806343,\"51,422.31\",28 Feb  "
                + "2013\n"
                + ",10010396,ACCENTURE EUROPEAN SERVICE CENTRE,544075,Grants "
                + "to External Organisations,5107785746,\"50,000.00\",04 Feb "
                + " 2013\n"
                + ",15500400,LONDON BOROUGH OF SOUTHWARK,544075,Grants to "
                + "External Organisations,5107807567,\"50,000.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017055,\"50,000.00\",07 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107730369,\"49,054.00\",06 Feb "
                + " 2013";
        String pattern = "LONDON";
        List<Integer> expected = new ArrayList<>();
        expected.add(855);
        expected.add(1676);
        expected.add(2012);
        expected.add(2112);
        expected.add(2455);
        expected.add(2710);
        expected.add(3601);
        expected.add(5089);
        expected.add(5664);
        expected.add(5989);
        expected.add(6549);
        expected.add(6662);
        expected.add(8514);
        expected.add(10137);
        expected.add(10371);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 1980.", 1980, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void searchRawCSVFileCityBruteForce() {
        //Raw CSV File
        //Source: http://datapipes.okfnlabs.org/csv/?url=https://raw.github.com/okfn/datapipes/master/test/data/gla.csv
        String text = ",Report of all payments made by GLA & GLA Land & "
                + "Property for value equal to or greater than � 250.00 Excl."
                + " VAT,,,,,,\n"
                + ",Reporting Period : ,12,,,,,\n"
                + ",Start Date:,\"3rd February, 2013\",,,,,\n"
                + ",End Date:,\"2nd March, 2013\",,,,,\n"
                + ",Financial Year :,2012 / 13,,,,,\n"
                + ",,,,,,,\n"
                + ",Vendor ID,Vendor Name,Cost Element,Expenditure Account "
                + "Code Description,Document No,Amount,Clearing Date\n"
                + ",18000063,CIRCLE ANGLIA LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017038,\"4,007,633.00\",06 Feb  "
                + "2013\n"
                + ",18000050,ONE HOUSING GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017214,\"3,047,294.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017060,\"2,850,000.00\",07 Feb  "
                + "2013\n"
                + ",18000078,BERKELEY PARTNERSHIP HOMES LIMITED,544078,HSG "
                + "Grants to Non-Registered Providers,1900017134,\"2,453,235"
                + ".00\",13 Feb  2013\n"
                + ",15500097,LONDON BOROUGH OF CAMDEN,544075,Grants to "
                + "External Organisations,5107795327,\"1,829,793.00\",14 Feb "
                + " 2013\n"
                + ",18000092,WANDSWORTH COUNCIL,544077,HSG Grants to Local "
                + "Authorities,1900017174,\"1,814,500.00\",20 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017201,\"1,597,410.00\",27 Feb  "
                + "2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017178,\"1,584,000.00\",22 Feb"
                + "  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017200,\"1,479,161.00\",27 Feb  "
                + "2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107785735,\"1,300,000.00\",13 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017062,\"1,278,125.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017037,\"1,113,030.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017025,\"1,035,334.00\",05 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017070,\"962,564.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017034,\"816,497.00\",06 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800156,\"743,130.35\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017073,\"709,088.00\",07 Feb "
                + " 2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017075,\"667,500.00\",08 Feb  "
                + "2013\n"
                + ",18000045,LONDON AND QUADRANT HOUSING TRUST,544076,HSG "
                + "Grants to Registered Providers,1900017027,\"617,342.00\","
                + "05 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017177,\"528,000.00\",22 Feb  "
                + "2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542300,Legal Fees,"
                + "5107780290,\"525,000.00\",20 Feb  2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017046,\"472,500.00\",06 Feb  "
                + "2013\n"
                + ",10002550,CITY OF WESTMINSTER,544075,Grants to External "
                + "Organisations,5107779502,\"423,560.00\",19 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017076,\"408,000.00\",07 Feb  "
                + "2013\n"
                + ",18000031,ORBIT HOUSING GROUP LTD,544076,HSG Grants to "
                + "Registered Providers,1900017071,\"367,328.00\",07 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017068,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017069,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107795798,\"349,573.20\",14 Feb  2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542320,Shared Services "
                + "Fees,5107786290,\"323,954.10\",12 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017171,\"312,183.00\",20 Feb  "
                + "2013\n"
                + ",18000051,VIRIDIAN HOUSING,544076,HSG Grants to Registered"
                + " Providers,1900017173,\"307,500.00\",20 Feb  2013\n"
                + ",10022339,TFL GROUP PROPERTY,510051,LAND/GROUND RENTS,"
                + "5107788855,\"300,854.40\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017065,\"293,094.00\",07 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017072,\"282,364.00\",07 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775201,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775210,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107778828,\"245,866.25\",12 Feb  2013\n"
                + ",18000066,EAST THAMES GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017077,\"240,582.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017101,\"234,137.00\",08 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017067,\"225,000.00\","
                + "07 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768186,\"211,670.60\",19 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017064,\"211,309.00\",07 Feb  "
                + "2013\n"
                + ",10002766,LONDON COUNCILS,550035,CCS Adjudication Services"
                + " Contract,5107799626,\"205,152.26\",22 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017102,\"203,312.00\",08 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017024,\"188,243.00\",05 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017057,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017066,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",10000044,CITY OF LONDON,544075,Grants to External "
                + "Organisations,5107772234,\"175,725.00\",06 Feb  2013\n"
                + ",10020714,BRITISH FASHION COUNCIL,544075,Grants to "
                + "External Organisations,5107785702,\"174,678.22\",06 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017103,\"174,267.00\",08 Feb  "
                + "2013\n"
                + ",10013679,SAFER LONDON FOUNDATION,544075,Grants to "
                + "External Organisations,5107788674,\"173,685.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017032,\"169,418.00\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017056,\"162,830.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017142,\"156,092.00\",14 Feb  "
                + "2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017047,\"145,098.00\",06 Feb  "
                + "2013\n"
                + ",10007104,LONDON BOROUGH OF ENFIELD,544075,Grants to "
                + "External Organisations,5107804988,\"143,440.61\",26 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800155,\"136,526.07\",20 Feb  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017030,\"133,118.00\",06 Feb  "
                + "2013\n"
                + ",10021258,SEETEC,544075,Grants to External Organisations,"
                + "5107790660,\"128,493.73\",25 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017031,\"126,309.00\","
                + "06 Feb  2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107802075,\"116,195.93\",25 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017172,\"116,178.00\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017033,\"115,500.00\",06 Feb "
                + " 2013\n"
                + ",10021972,ROOFF LIMITED,550010,Contracted Services "
                + "Buildings,5107796654,\"114,740.00\",18 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900016988,\"113,305.00\","
                + "04 Feb  2013\n"
                + ",18000121,ELDON HOUSING ASSOCIATION LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017085,\"109,890.00\","
                + "07 Feb  2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017074,\"105,000.00\",08 Feb  "
                + "2013\n"
                + ",15050001,HMRC,544070,MISCELLANEOUS FINANCE EXPENSES,"
                + "1900000632,\"102,537.00\",26 Feb  2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800887,\"101,888.90\",21 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017141,\"101,656.00\",14 Feb  "
                + "2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107789866,\"99,490.75\",07 Feb  2013\n"
                + ",10014839,LOCOG,544075,Grants to External Organisations,"
                + "5107737034,\"95,000.00\",14 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768150,\"90,715.97\",19 Feb  2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017036,\"83,477.00\",06 Feb  2013\n"
                + ",10020632,ST MUNGOS,544075,Grants to External "
                + "Organisations,5107769950,\"79,000.00\",06 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017058,\"78,046.00\",07 Feb  2013\n"
                + ",10008276,LPFA,501000,Pension Augmentation,1900017143,"
                + "\"77,922.97\",14 Feb  2013\n"
                + ",10014278,R.B KENSINGTON & CHELSEA,544075,Grants to "
                + "External Organisations,5107777970,\"74,069.00\",13 Feb  "
                + "2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800893,\"70,195.60\",21 Feb  2013\n"
                + ",10018194,AMATEUR SWIMMING ASSOCIATION,544075,Grants to "
                + "External Organisations,5107779983,\"70,000.00\",28 Feb  "
                + "2013\n"
                + ",10018127,GARDINER & THEOBALD FAIRWAY LTD,542000,"
                + "Management & Support Consultancy,5107786283,\"68,500.25\","
                + "08 Feb  2013\n"
                + ",10018379,JACOBS U.K LIMITED,542000,Management & Support "
                + "Consultancy,5107768420,\"67,563.75\",05 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017061,\"66,559.00\",07 Feb  2013\n"
                + ",10022226,DEPARTMENT OF HEALTH,544075,Grants to External "
                + "Organisations,5107788401,\"65,900.83\",26 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107788928,\"65,726.00\",06 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017063,\"62,161.00\",07 Feb  2013\n"
                + ",10018467,SOUND & LIGHT PRODUCTIONS,540110,Marketing "
                + "Exhibitions & Events,5107804862,\"58,000.00\",01 Mar  2013\n"
                + ",10001984,TURNER & TOWNSEND PROJECT MAGAG,542000,"
                + "Management & Support Consultancy,5107788132,\"53,299.35\","
                + "26 Feb  2013\n"
                + ",10006953,LONDON BOROUGH OF REDBRIDGE,544075,Grants to "
                + "External Organisations,5107806343,\"51,422.31\",28 Feb  "
                + "2013\n"
                + ",10010396,ACCENTURE EUROPEAN SERVICE CENTRE,544075,Grants "
                + "to External Organisations,5107785746,\"50,000.00\",04 Feb "
                + " 2013\n"
                + ",15500400,LONDON BOROUGH OF SOUTHWARK,544075,Grants to "
                + "External Organisations,5107807567,\"50,000.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017055,\"50,000.00\",07 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107730369,\"49,054.00\",06 Feb "
                + " 2013";
        String pattern = "LONDON";
        List<Integer> expected = new ArrayList<>();
        expected.add(855);
        expected.add(1676);
        expected.add(2012);
        expected.add(2112);
        expected.add(2455);
        expected.add(2710);
        expected.add(3601);
        expected.add(5089);
        expected.add(5664);
        expected.add(5989);
        expected.add(6549);
        expected.add(6662);
        expected.add(8514);
        expected.add(10137);
        expected.add(10371);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 10879.", 10879, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void searchRawCSVForCityKMP() {
        //Raw CSV File
        //Source: http://datapipes.okfnlabs.org/csv/?url=https://raw.github.com/okfn/datapipes/master/test/data/gla.csv
        String text = ",Report of all payments made by GLA & GLA Land & "
                + "Property for value equal to or greater than � 250.00 Excl."
                + " VAT,,,,,,\n"
                + ",Reporting Period : ,12,,,,,\n"
                + ",Start Date:,\"3rd February, 2013\",,,,,\n"
                + ",End Date:,\"2nd March, 2013\",,,,,\n"
                + ",Financial Year :,2012 / 13,,,,,\n"
                + ",,,,,,,\n"
                + ",Vendor ID,Vendor Name,Cost Element,Expenditure Account "
                + "Code Description,Document No,Amount,Clearing Date\n"
                + ",18000063,CIRCLE ANGLIA LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017038,\"4,007,633.00\",06 Feb  "
                + "2013\n"
                + ",18000050,ONE HOUSING GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017214,\"3,047,294.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017060,\"2,850,000.00\",07 Feb  "
                + "2013\n"
                + ",18000078,BERKELEY PARTNERSHIP HOMES LIMITED,544078,HSG "
                + "Grants to Non-Registered Providers,1900017134,\"2,453,235"
                + ".00\",13 Feb  2013\n"
                + ",15500097,LONDON BOROUGH OF CAMDEN,544075,Grants to "
                + "External Organisations,5107795327,\"1,829,793.00\",14 Feb "
                + " 2013\n"
                + ",18000092,WANDSWORTH COUNCIL,544077,HSG Grants to Local "
                + "Authorities,1900017174,\"1,814,500.00\",20 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017201,\"1,597,410.00\",27 Feb  "
                + "2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017178,\"1,584,000.00\",22 Feb"
                + "  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017200,\"1,479,161.00\",27 Feb  "
                + "2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107785735,\"1,300,000.00\",13 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017062,\"1,278,125.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017037,\"1,113,030.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017025,\"1,035,334.00\",05 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017070,\"962,564.00\",07 Feb  "
                + "2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017034,\"816,497.00\",06 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800156,\"743,130.35\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017073,\"709,088.00\",07 Feb "
                + " 2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017075,\"667,500.00\",08 Feb  "
                + "2013\n"
                + ",18000045,LONDON AND QUADRANT HOUSING TRUST,544076,HSG "
                + "Grants to Registered Providers,1900017027,\"617,342.00\","
                + "05 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017177,\"528,000.00\",22 Feb  "
                + "2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542300,Legal Fees,"
                + "5107780290,\"525,000.00\",20 Feb  2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017046,\"472,500.00\",06 Feb  "
                + "2013\n"
                + ",10002550,CITY OF WESTMINSTER,544075,Grants to External "
                + "Organisations,5107779502,\"423,560.00\",19 Feb  2013\n"
                + ",18000064,AFFINITY HOMES GROUP LIMITED,544076,HSG Grants "
                + "to Registered Providers,1900017076,\"408,000.00\",07 Feb  "
                + "2013\n"
                + ",18000031,ORBIT HOUSING GROUP LTD,544076,HSG Grants to "
                + "Registered Providers,1900017071,\"367,328.00\",07 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017068,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017069,\"360,000.00\","
                + "07 Feb  2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107795798,\"349,573.20\",14 Feb  2013\n"
                + ",10016524,TRANSPORT FOR LONDON,542320,Shared Services "
                + "Fees,5107786290,\"323,954.10\",12 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017171,\"312,183.00\",20 Feb  "
                + "2013\n"
                + ",18000051,VIRIDIAN HOUSING,544076,HSG Grants to Registered"
                + " Providers,1900017173,\"307,500.00\",20 Feb  2013\n"
                + ",10022339,TFL GROUP PROPERTY,510051,LAND/GROUND RENTS,"
                + "5107788855,\"300,854.40\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017065,\"293,094.00\",07 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017072,\"282,364.00\",07 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775201,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107775210,\"245,866.25\",12 Feb  2013\n"
                + ",10022587,METROPOLITAN HOME OWNERSHIP,542355,Other "
                + "Professional Fees,5107778828,\"245,866.25\",12 Feb  2013\n"
                + ",18000066,EAST THAMES GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017077,\"240,582.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017101,\"234,137.00\",08 Feb  "
                + "2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017067,\"225,000.00\","
                + "07 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768186,\"211,670.60\",19 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017064,\"211,309.00\",07 Feb  "
                + "2013\n"
                + ",10002766,LONDON COUNCILS,550035,CCS Adjudication Services"
                + " Contract,5107799626,\"205,152.26\",22 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017102,\"203,312.00\",08 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017024,\"188,243.00\",05 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017057,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",18000019,ISLINGTON AND SHOREDITCH HA LTD,544076,HSG "
                + "Grants to Registered Providers,1900017066,\"180,000.00\","
                + "07 Feb  2013\n"
                + ",10000044,CITY OF LONDON,544075,Grants to External "
                + "Organisations,5107772234,\"175,725.00\",06 Feb  2013\n"
                + ",10020714,BRITISH FASHION COUNCIL,544075,Grants to "
                + "External Organisations,5107785702,\"174,678.22\",06 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017103,\"174,267.00\",08 Feb  "
                + "2013\n"
                + ",10013679,SAFER LONDON FOUNDATION,544075,Grants to "
                + "External Organisations,5107788674,\"173,685.00\",06 Feb  "
                + "2013\n"
                + ",18000033,POPLAR HARCA LTD,544076,HSG Grants to Registered"
                + " Providers,1900017032,\"169,418.00\",06 Feb  2013\n"
                + ",18000011,HANOVER HOUSING ASSOCIATION,544076,HSG Grants to"
                + " Registered Providers,1900017056,\"162,830.00\",07 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017142,\"156,092.00\",14 Feb  "
                + "2013\n"
                + ",18000119,CATALYST COMMUNITIES HOUSING,544076,HSG Grants "
                + "to Registered Providers,1900017047,\"145,098.00\",06 Feb  "
                + "2013\n"
                + ",10007104,LONDON BOROUGH OF ENFIELD,544075,Grants to "
                + "External Organisations,5107804988,\"143,440.61\",26 Feb  "
                + "2013\n"
                + ",15500077,LONDON BOROUGH OF BARKING,544075,Grants to "
                + "External Organisations,5107800155,\"136,526.07\",20 Feb  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017030,\"133,118.00\",06 Feb  "
                + "2013\n"
                + ",10021258,SEETEC,544075,Grants to External Organisations,"
                + "5107790660,\"128,493.73\",25 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017031,\"126,309.00\","
                + "06 Feb  2013\n"
                + ",10007096,R B KINGSTON UPON THAMES,544075,Grants to "
                + "External Organisations,5107802075,\"116,195.93\",25 Feb  "
                + "2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017172,\"116,178.00\",20 Feb  "
                + "2013\n"
                + ",18000034,PARADIGM HOUSING GROUP LIMITED,544076,HSG Grants"
                + " to Registered Providers,1900017033,\"115,500.00\",06 Feb "
                + " 2013\n"
                + ",10021972,ROOFF LIMITED,550010,Contracted Services "
                + "Buildings,5107796654,\"114,740.00\",18 Feb  2013\n"
                + ",18000024,METROPOLITAN HOUSING TRUST LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900016988,\"113,305.00\","
                + "04 Feb  2013\n"
                + ",18000121,ELDON HOUSING ASSOCIATION LIMITED,544076,HSG "
                + "Grants to Registered Providers,1900017085,\"109,890.00\","
                + "07 Feb  2013\n"
                + ",18000035,AMICUS GROUP LIMITED,544076,HSG Grants to "
                + "Registered Providers,1900017074,\"105,000.00\",08 Feb  "
                + "2013\n"
                + ",15050001,HMRC,544070,MISCELLANEOUS FINANCE EXPENSES,"
                + "1900000632,\"102,537.00\",26 Feb  2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800887,\"101,888.90\",21 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017141,\"101,656.00\",14 Feb  "
                + "2013\n"
                + ",10020764,BROADWAY HOMELESSNESS AND SUPPORT,542800,"
                + "Hsg-Rough Sleeping,5107789866,\"99,490.75\",07 Feb  2013\n"
                + ",10014839,LOCOG,544075,Grants to External Organisations,"
                + "5107737034,\"95,000.00\",14 Feb  2013\n"
                + ",10002085,DRIVERS JONAS DELOITTE,542300,Legal Fees,"
                + "5107768150,\"90,715.97\",19 Feb  2013\n"
                + ",18000053,A2 DOMINION LONDON LTD,544076,HSG Grants to "
                + "Registered Providers,1900017036,\"83,477.00\",06 Feb  2013\n"
                + ",10020632,ST MUNGOS,544075,Grants to External "
                + "Organisations,5107769950,\"79,000.00\",06 Feb  2013\n"
                + ",18000023,OCTAVIA HOUSING AND CARE,544076,HSG Grants to "
                + "Registered Providers,1900017058,\"78,046.00\",07 Feb  2013\n"
                + ",10008276,LPFA,501000,Pension Augmentation,1900017143,"
                + "\"77,922.97\",14 Feb  2013\n"
                + ",10014278,R.B KENSINGTON & CHELSEA,544075,Grants to "
                + "External Organisations,5107777970,\"74,069.00\",13 Feb  "
                + "2013\n"
                + ",13002583,OSBORNE ENERGY LTD,550010,Contracted Services "
                + "Buildings,5107800893,\"70,195.60\",21 Feb  2013\n"
                + ",10018194,AMATEUR SWIMMING ASSOCIATION,544075,Grants to "
                + "External Organisations,5107779983,\"70,000.00\",28 Feb  "
                + "2013\n"
                + ",10018127,GARDINER & THEOBALD FAIRWAY LTD,542000,"
                + "Management & Support Consultancy,5107786283,\"68,500.25\","
                + "08 Feb  2013\n"
                + ",10018379,JACOBS U.K LIMITED,542000,Management & Support "
                + "Consultancy,5107768420,\"67,563.75\",05 Feb  2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017061,\"66,559.00\",07 Feb  2013\n"
                + ",10022226,DEPARTMENT OF HEALTH,544075,Grants to External "
                + "Organisations,5107788401,\"65,900.83\",26 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107788928,\"65,726.00\",06 Feb "
                + " 2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017063,\"62,161.00\",07 Feb  2013\n"
                + ",10018467,SOUND & LIGHT PRODUCTIONS,540110,Marketing "
                + "Exhibitions & Events,5107804862,\"58,000.00\",01 Mar  2013\n"
                + ",10001984,TURNER & TOWNSEND PROJECT MAGAG,542000,"
                + "Management & Support Consultancy,5107788132,\"53,299.35\","
                + "26 Feb  2013\n"
                + ",10006953,LONDON BOROUGH OF REDBRIDGE,544075,Grants to "
                + "External Organisations,5107806343,\"51,422.31\",28 Feb  "
                + "2013\n"
                + ",10010396,ACCENTURE EUROPEAN SERVICE CENTRE,544075,Grants "
                + "to External Organisations,5107785746,\"50,000.00\",04 Feb "
                + " 2013\n"
                + ",15500400,LONDON BOROUGH OF SOUTHWARK,544075,Grants to "
                + "External Organisations,5107807567,\"50,000.00\",01 Mar  "
                + "2013\n"
                + ",18000010,NOTTING HILL HOUSING TRUST,544076,HSG Grants to "
                + "Registered Providers,1900017055,\"50,000.00\",07 Feb  2013\n"
                + ",10019213,NSA ACADEMY FOR SPORT AND LEISURE,544075,Grants "
                + "to External Organisations,5107730369,\"49,054.00\",06 Feb "
                + " 2013";
        String pattern = "LONDON";
        List<Integer> expected = new ArrayList<>();
        expected.add(855);
        expected.add(1676);
        expected.add(2012);
        expected.add(2112);
        expected.add(2455);
        expected.add(2710);
        expected.add(3601);
        expected.add(5089);
        expected.add(5664);
        expected.add(5989);
        expected.add(6549);
        expected.add(6662);
        expected.add(8514);
        expected.add(10137);
        expected.add(10371);
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 10806.", 10806, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void makingTheMostOutOfTheQuarantine() {
        //Tips on how to make the most out of the quarantine
        //Source: https://adaa.org/learn-from-us/from-the-experts/blog-posts/consumer/covid-19-lockdown-guide-how-manage-anxiety-and
        String text = "1.) Reframe “I am stuck inside” to “I can finally "
                + "focus on my home and myself”\n"
                + "\n"
                + "As dismal as the world may feel right now, think of the "
                + "mandated work-from-home policy as an opportunity to "
                + "refocus your attention from the external to the internal. "
                + "Doing one productive thing per day can lead to a more "
                + "positive attitude. Set your sights on long-avoided tasks, "
                + "reorganize, or create something you’ve always wanted to. "
                + "Approaching this time with a mindset of feeling trapped or"
                + " stuck will only stress you out more. This is your chance "
                + "to slow down and focus on yourself.\n"
                + "\n"
                + "2.) Stay close to your normal routine\n"
                + "\n"
                + "Try and maintain some semblance of structure from the "
                + "pre-quarantine days. For those individu-als with children,"
                + " sticking to a routine might be easier; however as you "
                + "work from home, it could be tempting to fall into a more "
                + "lethargic lifestyle, which could lead to negative thinking"
                + ". Wake up and go to bed around the same time, eat meals, "
                + "shower, adapt your exercise regimen, and get out of your "
                + "PJ’s. Do laundry on Sundays as usual. Not only will "
                + "sticking to your normal routine keep you active and less "
                + "likely to spiral, it will be easier to readjust to the "
                + "outside world when it’s time to get back to work.\n"
                + "\n"
                + "3.) Avoid obsessing over endless Coronavirus coverage\n"
                + "\n"
                + "Freeing up your day from work or social obligations gives "
                + "you plenty of time to obsess, and if you have a tendency "
                + "to consult Google for every itch and sneeze, you may be "
                + "over-researching the pandemic as well. Choosing only "
                + "certain credible websites (who.int or cdc.gov is a good "
                + "start) for a limited amount of time each day (perhaps two "
                + "chunks of 30 minutes each) will be in your best interest "
                + "during this time.\n"
                + "\n"
                + "4.) A chaotic home can lead to a chaotic mind\n"
                + "\n"
                + "With all the uncertainly happening outside your home, keep"
                + " the inside organized, predictable and clean. Setting up "
                + "mental zones for daily activities can be helpful to "
                + "organize your day. For exam-ple, try not to eat in bed or "
                + "work on the sofa- just as before, eat at the kitchen table"
                + " and work at your desk. Loosening these boundaries just "
                + "muddles your routine and can make the day feel very long. "
                + "Additionally, a cluttered home can cause you to become "
                + "uneasy and claustrophobic of your environment- so keep it "
                + "tidy.\n"
                + "\n"
                + "5.) Start a new quarantine ritual\n"
                + "\n"
                + "With this newfound time, why not do something special "
                + "during these quarantined days? For ex-ample, perhaps you "
                + "can start a daily journal to jot down thoughts and "
                + "feelings to reflect on later. Or take a walk every day at "
                + "4pm, connect with your sister over FaceTime every morning,"
                + " or start a watercolor painting which you can add to "
                + "everyday. Having something special during this time will "
                + "help you look forward to each new day.";
        String pattern = "healthy";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.kmp(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 2786", 2786, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void makingMostOutOfTheCrisisBruteForce() {
        //Tips on how to make the most out of the quarantine
        //Source: https://adaa.org/learn-from-us/from-the-experts/blog-posts/consumer/covid-19-lockdown-guide-how-manage-anxiety-and
        String text = "1.) Reframe “I am stuck inside” to “I can finally "
                + "focus on my home and myself”\n"
                + "\n"
                + "As dismal as the world may feel right now, think of the "
                + "mandated work-from-home policy as an opportunity to "
                + "refocus your attention from the external to the internal. "
                + "Doing one productive thing per day can lead to a more "
                + "positive attitude. Set your sights on long-avoided tasks, "
                + "reorganize, or create something you’ve always wanted to. "
                + "Approaching this time with a mindset of feeling trapped or"
                + " stuck will only stress you out more. This is your chance "
                + "to slow down and focus on yourself.\n"
                + "\n"
                + "2.) Stay close to your normal routine\n"
                + "\n"
                + "Try and maintain some semblance of structure from the "
                + "pre-quarantine days. For those individu-als with children,"
                + " sticking to a routine might be easier; however as you "
                + "work from home, it could be tempting to fall into a more "
                + "lethargic lifestyle, which could lead to negative thinking"
                + ". Wake up and go to bed around the same time, eat meals, "
                + "shower, adapt your exercise regimen, and get out of your "
                + "PJ’s. Do laundry on Sundays as usual. Not only will "
                + "sticking to your normal routine keep you active and less "
                + "likely to spiral, it will be easier to readjust to the "
                + "outside world when it’s time to get back to work.\n"
                + "\n"
                + "3.) Avoid obsessing over endless Coronavirus coverage\n"
                + "\n"
                + "Freeing up your day from work or social obligations gives "
                + "you plenty of time to obsess, and if you have a tendency "
                + "to consult Google for every itch and sneeze, you may be "
                + "over-researching the pandemic as well. Choosing only "
                + "certain credible websites (who.int or cdc.gov is a good "
                + "start) for a limited amount of time each day (perhaps two "
                + "chunks of 30 minutes each) will be in your best interest "
                + "during this time.\n"
                + "\n"
                + "4.) A chaotic home can lead to a chaotic mind\n"
                + "\n"
                + "With all the uncertainly happening outside your home, keep"
                + " the inside organized, predictable and clean. Setting up "
                + "mental zones for daily activities can be helpful to "
                + "organize your day. For exam-ple, try not to eat in bed or "
                + "work on the sofa- just as before, eat at the kitchen table"
                + " and work at your desk. Loosening these boundaries just "
                + "muddles your routine and can make the day feel very long. "
                + "Additionally, a cluttered home can cause you to become "
                + "uneasy and claustrophobic of your environment- so keep it "
                + "tidy.\n"
                + "\n"
                + "5.) Start a new quarantine ritual\n"
                + "\n"
                + "With this newfound time, why not do something special "
                + "during these quarantined days? For ex-ample, perhaps you "
                + "can start a daily journal to jot down thoughts and "
                + "feelings to reflect on later. Or take a walk every day at "
                + "4pm, connect with your sister over FaceTime every morning,"
                + " or start a watercolor painting which you can add to "
                + "everyday. Having something special during this time will "
                + "help you look forward to each new day.";
        String pattern = "day";
        List<Integer> expected = new ArrayList<>();
        expected.add(277);
        expected.add(676);
        expected.add(1026);
        expected.add(1289);
        expected.add(1594);
        expected.add(1915);
        expected.add(2111);
        expected.add(2365);
        expected.add(2499);
        expected.add(2622);
        expected.add(2708);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 2812", 2812, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void stayingProductiveBoyerMoore() {
        //Tips on how to make the most out of the quarantine
        //Source: https://adaa.org/learn-from-us/from-the-experts/blog-posts/consumer/covid-19-lockdown-guide-how-manage-anxiety-and
        String text = "1.) Reframe “I am stuck inside” to “I can finally "
                + "focus on my home and myself”\n"
                + "\n"
                + "As dismal as the world may feel right now, think of the "
                + "mandated work-from-home policy as an opportunity to "
                + "refocus your attention from the external to the internal. "
                + "Doing one productive thing per day can lead to a more "
                + "positive attitude. Set your sights on long-avoided tasks, "
                + "reorganize, or create something you’ve always wanted to. "
                + "Approaching this time with a mindset of feeling trapped or"
                + " stuck will only stress you out more. This is your chance "
                + "to slow down and focus on yourself.\n"
                + "\n"
                + "2.) Stay close to your normal routine\n"
                + "\n"
                + "Try and maintain some semblance of structure from the "
                + "pre-quarantine days. For those individu-als with children,"
                + " sticking to a routine might be easier; however as you "
                + "work from home, it could be tempting to fall into a more "
                + "lethargic lifestyle, which could lead to negative thinking"
                + ". Wake up and go to bed around the same time, eat meals, "
                + "shower, adapt your exercise regimen, and get out of your "
                + "PJ’s. Do laundry on Sundays as usual. Not only will "
                + "sticking to your normal routine keep you active and less "
                + "likely to spiral, it will be easier to readjust to the "
                + "outside world when it’s time to get back to work.\n"
                + "\n"
                + "3.) Avoid obsessing over endless Coronavirus coverage\n"
                + "\n"
                + "Freeing up your day from work or social obligations gives "
                + "you plenty of time to obsess, and if you have a tendency "
                + "to consult Google for every itch and sneeze, you may be "
                + "over-researching the pandemic as well. Choosing only "
                + "certain credible websites (who.int or cdc.gov is a good "
                + "start) for a limited amount of time each day (perhaps two "
                + "chunks of 30 minutes each) will be in your best interest "
                + "during this time.\n"
                + "\n"
                + "4.) A chaotic home can lead to a chaotic mind\n"
                + "\n"
                + "With all the uncertainly happening outside your home, keep"
                + " the inside organized, predictable and clean. Setting up "
                + "mental zones for daily activities can be helpful to "
                + "organize your day. For exam-ple, try not to eat in bed or "
                + "work on the sofa- just as before, eat at the kitchen table"
                + " and work at your desk. Loosening these boundaries just "
                + "muddles your routine and can make the day feel very long. "
                + "Additionally, a cluttered home can cause you to become "
                + "uneasy and claustrophobic of your environment- so keep it "
                + "tidy.\n"
                + "\n"
                + "5.) Start a new quarantine ritual\n"
                + "\n"
                + "With this newfound time, why not do something special "
                + "during these quarantined days? For ex-ample, perhaps you "
                + "can start a daily journal to jot down thoughts and "
                + "feelings to reflect on later. Or take a walk every day at "
                + "4pm, connect with your sister over FaceTime every morning,"
                + " or start a watercolor painting which you can add to "
                + "everyday. Having something special during this time will "
                + "help you look forward to each new day.";
        String pattern = "start";
        List<Integer> expected = new ArrayList<>();
        expected.add(1553);
        expected.add(2401);
        expected.add(2568);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text,
                comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount() + ". Should be 655", 655, comparator.getComparisonCount());
    }
}
