import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This set of JUnits assumes you have already passed all tests in the StudentTest
 * provided tests.
 *
 * If any tests do not work as expected, please let me know! Email me at tschott6@gatech.edu or
 * reply to my Piazza post.
 *
 *
 * TESTS INCLUDED:
 * Exception handling for every method that can throw an exception
 * Cases when pattern or text is empty
 * Cases when pattern or text is size = 1
 * 2 sets of tests with 3 cases each (run with each algorithm). These tests are a good way to see which algorithm
 *  does well in which scenario by comparing the number of comparisons.
 *
 * I relied on my algorithms for some of the comparison counts, so let me know if you get something different.
 *
 * NOTE:
 * There are multiple inner classes that can be run separately or together. There is one for each set
 * of tests I could think of. They should work with the given imports, but may require more.
 * Tested for IntelliJ.
 *
 *
 * @author Tyler Schott
 * @version 1.1
 */

@RunWith(SchottJUnit09.class)
@Suite.SuiteClasses({ SchottJUnit09.TestExceptions.class, SchottJUnit09.TestEmpty.class,
        SchottJUnit09.TestSize1Pattern.class, SchottJUnit09.TestTextRepeatingA.class,
        SchottJUnit09.TestTextRepeatingAWithBInMiddle.class, SchottJUnit09.FunTests.class})
public class SchottJUnit09 extends Suite {

    public SchottJUnit09(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestExceptions {

        private static final int TIMEOUT = 200;

        @Test(timeout = TIMEOUT)
        public void testBruteNulls() {

            int numExceptions = 0;

            try {
                PatternMatching.bruteForce(null, "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.bruteForce("", "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.bruteForce("a", null, new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.bruteForce("a", "ab", null);
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }

            assertEquals(4, numExceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testKMPNulls() {

            int numExceptions = 0;

            try {
                PatternMatching.kmp(null, "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.kmp("", "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.kmp("a", null, new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.kmp("a", "ab", null);
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }

            assertEquals(4, numExceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testFailureTableNulls() {

            int numExceptions = 0;

            try {
                PatternMatching.buildFailureTable(null, new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.buildFailureTable("a", null);
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            //Should not throw exception
            try {
                PatternMatching.buildFailureTable("", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }

            assertEquals(2, numExceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testBMNulls() {

            int numExceptions = 0;

            try {
                PatternMatching.boyerMoore(null, "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.boyerMoore("", "ab", new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.boyerMoore("a", null, new CharacterComparator());
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            try {
                PatternMatching.boyerMoore("a", "ab", null);
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }

            assertEquals(4, numExceptions);
        }

        @Test(timeout = TIMEOUT)
        public void testLastTableNulls() {

            int numExceptions = 0;

            try {
                PatternMatching.buildLastTable(null);
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }
            //Should not throw exception
            try {
                PatternMatching.buildLastTable("");
            } catch (IllegalArgumentException ex) {
                numExceptions++;
            }

            assertEquals(1, numExceptions);
        }
    }

    public static class TestEmpty {

        private static final int TIMEOUT = 200;
        private CharacterComparator comparator;

        @Before
        public void setup() {
            comparator = new CharacterComparator();
        }

        @Test(timeout = TIMEOUT)
        public void testEmptyFailureTable() {
            int[] ft = PatternMatching.buildFailureTable("", comparator);
            assertArrayEquals(new int[0], ft);
        }

        @Test(timeout = TIMEOUT)
        public void testEmptyLastTable() {
            Map<Character, Integer> actual = PatternMatching.buildLastTable("");
            Map<Character, Integer> expected = new HashMap<>();
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testEmptyTextBrute() {
            List<Integer> actual = PatternMatching.bruteForce("a", "", comparator);
            List<Integer> expected = new ArrayList<>();
            assertEquals(expected, actual);
            assertEquals(0, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testEmptyTextBM() {
            List<Integer> actual = PatternMatching.boyerMoore("a", "", comparator);
            List<Integer> expected = new ArrayList<>();
            assertEquals(expected, actual);
            assertEquals(0, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testEmptyTextKMP() {
            List<Integer> actual = PatternMatching.kmp("a", "", comparator);
            List<Integer> expected = new ArrayList<>();
            assertEquals(expected, actual);
            assertEquals(0, comparator.getComparisonCount());
        }
    }

    public static class TestSize1Pattern {

        private static final int TIMEOUT = 200;
        private CharacterComparator comparator;
        private String pattern;

        @Before
        public void setup() {
            comparator = new CharacterComparator();
            pattern = "a";
        }

        @Test(timeout = TIMEOUT)
        public void testTextLen1Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0}));
            List<Integer> actual = PatternMatching.bruteForce(pattern, "a", comparator);

            assertEquals(expected, actual);
            assertEquals(1, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTextLen1BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0}));
            List<Integer> actual = PatternMatching.boyerMoore(pattern, "a", comparator);

            assertEquals(expected, actual);
            assertEquals(1, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTextLen1KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0}));
            List<Integer> actual = PatternMatching.kmp(pattern, "a", comparator);

            assertEquals(expected, actual);
            assertEquals(1, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{1, 4, 7, 10, 16}));
            List<Integer> actual = PatternMatching.bruteForce(pattern, "badeayqairambetya", comparator);

            assertEquals(expected, actual);
            assertEquals(17, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{1, 4, 7, 10, 16}));
            List<Integer> actual = PatternMatching.boyerMoore(pattern, "badeayqairambetya", comparator);

            assertEquals(expected, actual);
            assertEquals(17, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{1, 4, 7, 10, 16}));
            List<Integer> actual = PatternMatching.kmp(pattern, "badeayqairambetya", comparator);

            assertEquals(expected, actual);
            assertEquals(17, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
            List<Integer> actual = PatternMatching.bruteForce(pattern, "aaaaaaaaaa", comparator);

            assertEquals(expected, actual);
            assertEquals(10, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
            List<Integer> actual = PatternMatching.boyerMoore(pattern, "aaaaaaaaaa", comparator);

            assertEquals(expected, actual);
            assertEquals(10, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
            List<Integer> actual = PatternMatching.kmp(pattern, "aaaaaaaaaa", comparator);

            assertEquals(expected, actual);
            assertEquals(10, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatNotInBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.bruteForce(pattern, "bbbbbb", comparator);

            assertEquals(expected, actual);
            assertEquals(6, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatNotInBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.boyerMoore(pattern, "bbbbbb", comparator);

            assertEquals(expected, actual);
            assertEquals(6, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testLongTextRepeatNotInKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.kmp(pattern, "bbbbbb", comparator);

            assertEquals(expected, actual);
            assertEquals(6, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testBuildLast() {
            Map<Character, Integer> actual = PatternMatching.buildLastTable(pattern);
            Map<Character, Integer> expected = new HashMap<>();
            expected.put('a', 0);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testBuildFailure() {
            int[] actual = PatternMatching.buildFailureTable("a", comparator);
            int[] expected = new int[]{0};

            assertArrayEquals(expected, actual);
            assertEquals(0, comparator.getComparisonCount());
        }
    }

    public static class TestTextRepeatingA {

        private static final int TIMEOUT = 200;
        private CharacterComparator comparator;
        private String text;

        @Before
        public void setup() {
            comparator = new CharacterComparator();
            text = "aaaaaaaaaaaaaaaaaaaaaaa";
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.bruteForce("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(54, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.boyerMoore("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(72, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.kmp("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(44, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.bruteForce("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(18, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.boyerMoore("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(3, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.kmp("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(23, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.bruteForce("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(108, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.boyerMoore("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(108, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.kmp("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(28, comparator.getComparisonCount());
        }
    }

    public static class TestTextRepeatingAWithBInMiddle {

        private static final int TIMEOUT = 200;
        private CharacterComparator comparator;
        private String text;

        @Before
        public void setup() {
            comparator = new CharacterComparator();
            text = "aaaaaaaaaabaaaaaaaaaaaa";
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{8}));
            List<Integer> actual = PatternMatching.bruteForce("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(54, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{8}));
            List<Integer> actual = PatternMatching.boyerMoore("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(63, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AABAAA_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{8}));
            List<Integer> actual = PatternMatching.kmp("aabaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(40, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.bruteForce("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(19, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.boyerMoore("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(3, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_BBBBBB_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{}));
            List<Integer> actual = PatternMatching.kmp("bbbbbb", text, comparator);

            assertEquals(expected, actual);
            assertEquals(24, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_Brute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.bruteForce("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(93, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_BM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.boyerMoore("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(73, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void test_AAAAAA_KMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 11, 12, 13, 14, 15, 16, 17}));
            List<Integer> actual = PatternMatching.kmp("aaaaaa", text, comparator);

            assertEquals(expected, actual);
            assertEquals(33, comparator.getComparisonCount());
        }
    }

    /**
     * Ever wondered how many times CNN says "Coronavirus" in a broadcast? What about Donald Trump in a
     * Press Briefing? Or what about how many times Trump says "great" in an hour? Your code can help with that!
     */
    public static class FunTests {

        //CNN Townhall on Coronavirus in lowercase
        private String CNNtext;

        //Donald Trump Press Briefing
        private String TrumpText;

        private static final int TIMEOUT = 1000;
        private CharacterComparator comparator;

        @Before
        public void setup() {
            comparator = new CharacterComparator();

            CNNtext = "cnn global town hall: coronavirus facts and fears. aired 8-9p et\n" +
                    "aired march 26, 2020 - 20:00   et\n" +
                    "this is a rush transcript. this copy may not be in its final form and may be updated.\n" +
                    "\n" +
                    "[20:00:00]\n" +
                    "\n" +
                    "erin burnett, cnn host: well we are rooting for you, and rooting for you to see those little girls. and i hope that's a lesson for the health departments to learn here to -- those communications are crucial, because you're going to have all these people coming out of quarantine.\n" +
                    "\n" +
                    "thank you, craig. we wish you the best.\n" +
                    "\n" +
                    "thanks to all of you for joining us. cnn's global town hall coronavirus: facts and fears begins now.\n" +
                    "\n" +
                    "anderson cooper, cnn host: hello and welcome. i'm anderson cooper in new york.\n" +
                    "\n" +
                    "sanjay gupta, cnn chief medical correspondent: and i'm dr. sanjay gupta in atlanta. welcome to our fourth cnn global town hall: coronavirus: facts and fears.\n" +
                    "\n" +
                    "tonight, dr. anthony fauci will be joining us answering your questions about the pandemic. we also have bill gates whose bill & melinda gates foundation has pledged up to $100 million to fight the coronavirus.\n" +
                    "\n" +
                    "cooper: today was a bad day. more people died in america today from the virus than on any other day. and the u.s. now leads the world in the number of reported cases of coronavirus.\n" +
                    "\n" +
                    "as sanjay said, this is our fourth town hall, but this one is very different because we're following the latest safety guidelines from health professionals as always.\n" +
                    "\n" +
                    "sanjay and i are in separate studios without any crew or others around us and all of our guest are by remote as well.\n" +
                    "\n" +
                    "gupta: so it's going to look a little different from our other town halls. but as we said last week and in many times before, we're determined to keep reporting the story and bringing you as much knowledge as we can. and also answering as many of your questions as we can as well.\n" +
                    "\n" +
                    "cooper: we still have our social media scroll, which you'll see at the bottom of your screen. so please tweet us your questions with the hashtag, cnn town hall. you can also leave a comment on the cnn facebook page. a lot of you have also sent in video questions. we'll get as many of those as we can.\n" +
                    "\n" +
                    "and i know it's a cliche but we are all in this together. and tonight, we'll also have reports from across the country and around the world including europe and china, where our correspondents are. we start in the u.s. which has seen a significant rise in cases.\n" +
                    "\n" +
                    "(begin video tape)\n" +
                    "\n" +
                    "unidentified male: the world health organization is now warning that the u.s. could become the next global epicenter for the pandemic.\n" +
                    "\n" +
                    "cooper: yesterday was the deadliest day by far. the death rate is rising here.\n" +
                    "\n" +
                    "unidentified male: the situation is so severe the white house is advising people who visit or pass through new york city to self- quarantine themselves for 14 days.\n" +
                    "\n" +
                    "cooper: one week ago, there were 8,000 confirmed cased in the u.s. now, there are more than 80,000. and so far in this country more than 1,100 people have died.\n" +
                    "\n" +
                    "jerome adams, u.s. surgeon general: this week it's going to get bad and we really need to come together as a nation.\n" +
                    "\n" +
                    "cooper: at least 177 million americans are under stay at home orders, which is more than half the population. new york remains the epicenter in the u.s. with more than 30,000 cases in this state. more 18,000 in new york city alone.\n" +
                    "\n" +
                    "the enormous javits convention center is being turned into a field hospital with 1,000 beds, still not enough according to officials with the number of patients they're expecting.\n" +
                    "\n" +
                    "but there is a sign of hope. new york governor andrew cuomo says the stay at home measures he enacted seem to be working.\n" +
                    "\n" +
                    "gov. andrew cuomo (d-ny): that is almost too good to be true. but the theory is given the density that we're dealing with, it spreads very quickly, but if you reduce the density you can reduce the spread very quickly.\n" +
                    "\n" +
                    "cooper: the 15-day period of social distancing recommended by the white house task force comes to an end early next week. but scientist still can't say when they think the virus might be under control in this country.\n" +
                    "\n" +
                    "anthony fauci, director, national institute of allergy and infectious diseases: you've got be realistic, and you've got to understand that you don't make the timeline, the virus makes the timeline.\n" +
                    "\n" +
                    "(end video tape)\n" +
                    "\n" +
                    "cooper: the virus makes the timeline. as i mentioned at the top there are two great milestones today that we reached. sanjay, as you know, it's the deadliest day on record in the u.s. for the virus. the number's gone up since i recorded that report.\n" +
                    "\n" +
                    "the number of new deaths just today now at least 248. that's a record. and then total is 1,186 deaths from that pandemic in this country. and the second milestone that we learned just before we went on air, the u.s. now has surpassed china in coronavirus cases. we now have more than any other country in the world, 81,836 in the u.s. and sadly rising.\n" +
                    "\n" +
                    "and sanjay, with that in mind, as we've done right here in each of these town halls, i want to start off just by asking you what you think is the most important thing that you've learned this week that you didn't know before and the most important thing that we still don't know.\n" +
                    "\n" +
                    "gupta: well, we're certainly getting an idea, anderson, of just -- just how explosive this growth is. i mean we -- we keep anticipating this. but to see the numbers of the first time we did a town hall, anderson, 60 people were diagnosed. and now, you know, close to 90,000.\n" +
                    "\n" +
                    "we know that most americans are settling into this new reality doing their best to stay home as much as possible. we also know and confirm now that younger people can also be very much at risk.\n" +
                    "\n" +
                    "[20:05:00]\n" +
                    "\n" +
                    "i think for a long time the narrative was this was something that just affected the elderly. but 20 percent of those hospitalized, anderson, between the ages of 20 and 44.\n" +
                    "\n" +
                    "something good that we learned this week is that the virus isn't mutating much, that's good because it's not going to become more lethal, likely, and it's also going to be good for a vaccine if the virus doesn't mutate much.\n" +
                    "\n" +
                    "we still don't know when exactly this is going to end, i think that is still the biggest thing that we don't know, and exactly how - you know when this pullback, when people are going to be allowed to go back to work.\n" +
                    "\n" +
                    "all we know is it's still weeks away, but there is an end in sight, this isn't going to last forever, anderson.\n" +
                    "\n" +
                    "cooper: again, what we just reported a moment ago that the u.s. has now passed china for the most reported coronavirus cases. many believe the epicenter right now is new york city.\n" +
                    "\n" +
                    "want to go first to erica hill who's at elmhurst hospital in new york city, where mayor bill de blasio called the epicenter of the epicenter of the city's outbreak. erica, what's the latest?\n" +
                    "\n" +
                    "erica hill, cnn national correspondent: yes, he did, and there's been a lot of attention paid to this hospital because of what's coming in there (ph) and also because what is coming out of this hospital.\n" +
                    "\n" +
                    "what we're hearing in terms of accounts (ph) - medical professionals telling us at cnn earlier today they are bursting at the seams, that in terms of how people are feeling it's a state of panic.\n" +
                    "\n" +
                    "one of the e.r. doctors actually shared what she saw at her account of 72 hours in the e.r. here, with \"the new york times.\" here's a little bit of what she had to say.\n" +
                    "\n" +
                    "(begin video clip)\n" +
                    "\n" +
                    "unidentified female: i don't have the support that i need, and even just the materials that i need physically to take care of my patients, and it's america, and we're supposed to be a first world country.\n" +
                    "\n" +
                    "(end video clip)\n" +
                    "\n" +
                    "hill: part of what she said, is we're told it's all going to be fine - it is not fine here. and the emergency management commissioner for new york agreed with her earlier today, saying, anderson that her account was spot-on.\n" +
                    "\n" +
                    "gupta: and erica, you know, you and i were e-mailing earlier today, and you sort of pointed out that it isn't just this one hospital. you've been talking to e.r. doctors at other hospitals as well, and what are they telling you?\n" +
                    "\n" +
                    "hill: similar things, and i'm sure you're hearing the same thing too, sanjay. one of the doctors i spoke to said basically everybody coming in to the e.r. now has some sort of coronavirus-related issue.\n" +
                    "\n" +
                    "they are not seeing the heart attack, or the people who need stitches - even the sniffly (ph) noses that they used to. he's saying partially that's likely that these people are scared to come in - maybe they're worried about overwhelming the e.r. as well.\n" +
                    "\n" +
                    "he said it's taking a mental, physical, and emotional toll on the staff - everyone from the paramedics all the way through to discharge. and he said what's most important is that we keep repeating the message - social distancing, wash your hands and take all of that seriously.\n" +
                    "\n" +
                    "cooper: erica hill, thanks very much.\n" +
                    "\n" +
                    "i want to go now to china, which is closing its borders to foreigners fearing a second wave of the pandemic. david culver is in shanghai for us.\n" +
                    "\n" +
                    "david, so the borders will be closed with the fear of a second wave of cases. i'm wondering though, in a place like wuhan, as restrictions are loosened in various places that are hit by the virus, are there any signs that infected people who are asymptomatic are potentially spreading the virus still?\n" +
                    "\n" +
                    "david culver, cnn correspondent: this is a huge concern, anderson, because if you look at the numbers - and the numbers have been in question for several weeks now since the reporting started because of the source of all of this. the numbers being sourced from the national health commission, that is, the chinese government. so with regards to potential asymptomatic cases, they do believe those\n" +
                    "\n" +
                    "could be pretty active within the wuhan area, and even hubei province, and even really other parts of mainland china. the issue is those aren't officially counted towards the total number.\n" +
                    "\n" +
                    "so as you start to ease restrictions, and you mentioned in wuhan that's going to happen in about two weeks' time, already in other parts of hubei they've started to ease those - what some have described as brutal lockdown conditions. you're going to have people moving around again, you're going to have people starting to resume life - will that then cause the numbers to go back up?\n" +
                    "\n" +
                    "it's something that they're very mindful of here, and really quite hesitant of, even the residents that we've spoken with, anderson, have quite frankly told us as soon as the gates open, to so speak they're in no rush to get out of their homes after 70 plus days of lockdown.\n" +
                    "\n" +
                    "gupta: and david, you know, i think the timeline is so important here because people are looking to china to get an idea of what it might be like here in the united states. and for the first time now, i guess since the crisis began in hubei, 10 weeks or so of lockdown - some of those restrictions are being lifted.\n" +
                    "\n" +
                    "so what are the authorities expecting to happen here? how is it going to return to normal?\n" +
                    "\n" +
                    "culver: i think part of their expectation is to rely on that hesitation from some of the residents, right? they don't want to get to complacent, they don't want to think that they've got this beat.\n" +
                    "\n" +
                    "and residents that we've spoken with, sanjay, have told me quite frankly that you know they're going to watch to see how things unfold. yes, they want to get back to life - one young woman telling me she wants to get to starbucks, she wants to get to mcdonald's, but at the same time she doesn't fully trust that things are fully under control.\n" +
                    "\n" +
                    "that being said, you do see already extreme cleansing of some of the public transit, you see buses being sanitized, you see actions being taken to prepare for this.\n" +
                    "\n" +
                    "[20:10:07]\n" +
                    "\n" +
                    "and the other big concern is going to be imported cases which is why they've now essentially banned all foreign travelers into china - into mainland china.\n" +
                    "\n" +
                    "i mean, you go back a few weeks, sanjay, and it was the rest of the world that was really concerned with travelers coming from china. now it's china really worried about travelers coming from every other country. so that's why they've taken these extreme steps, but at the same time, anderson and sanjay, i think there is some optimism and cautious optimism from the people we've talked to. that life may be resuming.\n" +
                    "\n" +
                    "cooper: david culver - david, thanks very much. scott mclean joins us now from spain which reached another gruesome milestone of its own this week. more than 4,000 deaths surpassing the death toll of china. things are so bad and an ice rink has been converted into a morgue. and that's where scott is tonight.\n" +
                    "\n" +
                    "so, are the new cases still rising there as well as the deaths?\n" +
                    "\n" +
                    "scott mclean, cnn correspondent: hey, anderson. the increases here have been really startling. in just the last 24 hours, more than 650 have died from this coronavirus. the number of confirmed cases has jumped up by more than 8,500. and to put that in context, that is almost twice as many new cases as were reported in italy. the health minister has said that there are signs that spain is starting to enter this period of stabilization, but officials have been optimistic about seeing the peak of this pandemic for quite some time.\n" +
                    "\n" +
                    "it's also important to keep in mind that the true number of cases in spain is likely much, much higher than the official number and that's because spain has really struggled to get a handle on the amount of testing that its doing. it's really struggled to expand the testing beyond the 15 or 20,000 test that it's doing per day right now.\n" +
                    "\n" +
                    "case and point, officials acknowledged just this morning that a batch of several thousand tests that had been imported from china had to be sent back because they didn't work.\n" +
                    "\n" +
                    "gupta: yes. and scott, you know as anderson mentioned, you're standing in front of this -- this pretty grim scene here. i guess this ice rink recently converted into a morgue. is that -- is that just for people who've died of covid-19 or -- or other patients as well?\n" +
                    "\n" +
                    "mclean: only coronavirus patients here, sanjay. the reason that this ice rink started to be used in the first place is because the city state run funeral service, stopped picking up the bodies of coronavirus patients because they said they didn't have enough protective equipment.\n" +
                    "\n" +
                    "now the issue is more that there simply isn't enough space in the city morgues to store all of these bodies given the backlog of bodies waiting to be -- to be buried or to be cremated. and so they have to come somewhere and obviously this ice rink is -- is a suitable place for them.\n" +
                    "\n" +
                    "the problem is particularly acute here because more than half of all the coronavirus deaths in this country have been here in madrid.\n" +
                    "\n" +
                    "cooper: that's just terrible. everyone, thank you. stay with us. we're going to take a quick break. coming later tonight, bill gates joins us. he's been warning about a pandemic like this for a long time. but first, after the break, dr. anthony fauci joins us to answer your questions about the coronavirus. we'll be right back.\n" +
                    "\n" +
                    "[20:16:50]\n" +
                    "\n" +
                    "(commercial break) cooper: and welcome back to the cnn global town hall. our next guest is dr. anthony fauci. he's a member of the president's coronavirus task force and the director of the national institute of allergy and infectious diseases.\n" +
                    "\n" +
                    "gupta: and he is here to answer your questions, of course, about the coronavirus. dr. fauci, welcome.\n" +
                    "\n" +
                    "fauci: good to be with you.\n" +
                    "\n" +
                    "gupta: thank you very much for your service, sir. i know you're putting in very long hours, and we appreciate it. let me get right to it. you've been saying that we should wait for the data to decide whether or not to start pulling back on some of these recommendations and maybe allowing people to go back to work.\n" +
                    "\n" +
                    "i've been looking at the data. i know you have, as well. it seems pretty clear that the numbers are not only increasing, but accelerating in places that had no cases or very few cases last week --\n" +
                    "\n" +
                    "fauci: right.\n" +
                    "\n" +
                    "gupta: -- are now in the thousands. so why raise the idea that a pullback is even close, dr. fauci?\n" +
                    "\n" +
                    "fauci: well, i think what the president was trying to do, he was making an aspirational projection to give people some hope. but he's listening to us when we say we've really got to reevaluate it in real- time and any decision we make has to be based on the data.\n" +
                    "\n" +
                    "i mean, you know, the numbers that you showed, when you have a situation when the cases today compared to tomorrow is increased dramatically and then the next day is increased dramatically, that's no time to pull back. that's when you got to hunker down, nail down, mitigate, mitigate, mitigate, get the people taken care of. that's what you got to concentrate on. you have to go with the data.\n" +
                    "\n" +
                    "cooper: dr. fauci, as you mentioned, you said the virus makes the timeline. the notion, though, of people getting back to work in some places, it's sort of based on the idea that there's hot spots right now in new york city, california. we've seen washington. new orleans seems to be in trouble, and some other places seem to be coming up in terms of cases, but that there's places where they haven't seen so much.\n" +
                    "\n" +
                    "are you confident that the places that haven't had a lot of reported cases, that that's just not a question of testing, is it a question of there's simply -- the virus isn't there?\n" +
                    "\n" +
                    "fauci: well, it's probably a combination of both. but i think it's more that they have not yet had that kind of escalation that we've seen in cities like new york. and just the point about those areas that have low levels, like there about -- i think 19 out of 50 states have 200 cases or less in a state. that's the time, if you're going to do anything in those places, you've got to be very aggressive in identification, isolation, contact tracing. when people are infected, get them out of society, put them in a way where they're isolated and then trace the others. that's all containment.\n" +
                    "\n" +
                    "you don't want to get to the point where you have to start mitigating. so what we're talking about is trying to get to the point where we don't allow these issues to come up to the point of needing mitigation.\n" +
                    "\n" +
                    "but in order to do that, as i've said and as dr. birx has said, you need to get the data and you need to act on the data, because if it's escalating, there's no way you want to tone down. you want to be able to suppress what's there while it's at a low level.\n" +
                    "\n" +
                    "cooper: so you want to see more testing, especially in places where, you know, they have low numbers of cases in order to gather data.\n" +
                    "\n" +
                    "[20:20:09]\n" +
                    "\n" +
                    "i mean, are there people gathering that data? are they doing the contact tracing in states that have less than 200 cases?\n" +
                    "\n" +
                    "fauci: well, you know, today the president wrote a letter to the governors talking about sort of a new approach to this kind of county- by-county -- not mitigation, but contact tracing, where what you actually do is you do testing there.\n" +
                    "\n" +
                    "now, that's going to be through the public health apparatus, which the cdc has networks that are really for flu surveillance. and you could adapt those networks to be coronavirus surveillance. when you do that, then you could plug in the identification, isolation, and contact tracing.\n" +
                    "\n" +
                    "again, it's an attempt to do that. i hope they'll be successful, but that's what the plan is, to use that network to do the kind of surveillance we've done with flu.\n" +
                    "\n" +
                    "gupta: you know, dr. fauci, so we have, you know, sort of three months' worth of data now, 150 or so countries i think where this virus has been present. at what point do you say we have enough data, that we really can start to set a timeline? i know you say the virus will dictate the timeline, but, you know, do we -- we must know a lot more now than we did even a couple weeks ago.\n" +
                    "\n" +
                    "fauci: yes, we do know a lot more now than we did a couple of weeks ago, but the one thing that's still a little bit of a black box, and that's the thing that influences the modelling that we do, is that what is the relative percentage of really asymptomatic infection? because that influences everything.\n" +
                    "\n" +
                    "that influences transmission. that influences contact tracing. and that certainly influences the diameter of the models that you use. so if you're going to model how you do things, you really have to have the data of know what you're dealing with. that's why it's so important to do that. now that we have so many more tests available, we've really got to get out there and do that. cooper: i want to get to viewer questions, but i just want to quickly\n" +
                    "\n" +
                    "follow up on the question we were talking about before, which is in those states where it's low, you talked about the plan for contact tracing, the idea that -- is it actually being done now? or is that still something that needs to be increased?\n" +
                    "\n" +
                    "fauci: it needs to be ratcheted up, anderson. we can't -- i mean, we've got to do it better than we are now, not that we're at fault, that no one's made any mistakes, but they've got to elevate it to the point where when you have someone in society who is infected, you've got to not only identify them, but you've got to be able to isolate them very quickly, not five days later after they wound up potentially infecting individuals.\n" +
                    "\n" +
                    "so we've got to get that system where you identify somebody and as quickly as possible get them out of a situation where they may infect other people. that's what's called strict containment, and that's what we've got to do.\n" +
                    "\n" +
                    "cooper: all right. dr. fauci, we've got a lot of questions from viewers. edward sabatini in miami sent in this video. take a look.\n" +
                    "\n" +
                    "edward sabatini: hi, i have lupus and i take hydroxychloroquine. i've been taking this medication for three years now and it's the only drug that can help me function on a daily basis. my 90-day supply is up in less than 10 days. my pharmacist walgreens called me to tell me that they can't fill this prescription for me because this medication is no longer available in the marketplace.\n" +
                    "\n" +
                    "they went on to say that they don't expect to receive any more of this medication for the time being. all of this medication is being utilized in new york city for the covid-19 test. what am i supposed to do when i run out of my medication less than 10 days from now?\n" +
                    "\n" +
                    "cooper: dr. fauci?\n" +
                    "\n" +
                    "fauci: yes, i mean, that individual has a very good point, and that's the reason why i have said so often that we should be giving drugs for people with diseases that we know it works. and that's really one of those unintended consequences that's a negative consequence, is that when you use the drug for something in which it's not a proven benefit, those individuals who need the drug for a disease for which there is a proven benefit could potentially suffer, the same way the individual that you've just put on the program.\n" +
                    "\n" +
                    "gupta: and, dr. fauci, i mean, i don't want to make this political, but, i mean, this drug was sort of described as a game-changer. you understand why people may go out and want to get this drug. pharmacies i've been talking to say they've run out in all their pharmacies. was that just a mistake or too premature to present it that way?\n" +
                    "\n" +
                    "fauci: sanjay, i'm not going to pass judgment on that. you know that. that's not -- that's not really helpful. i can just tell you what i have said all along, and i'll say it again, the evidence that that works is anecdotal. it is not a definitive proof that that drug works, period. gupta: all right. let's get to another viewer question -- oh, sorry,\n" +
                    "\n" +
                    "anderson.\n" +
                    "\n" +
                    "cooper: no, no, go ahead.\n" +
                    "\n" +
                    "gupta: monica grinage-prince in houston, texas, sent in this video. let's take a look.\n" +
                    "\n" +
                    "[20:25:01]\n" +
                    "\n" +
                    "monica grinage-prince: is a sudden loss of smell and taste a symptom of coronavirus? and if so, and the person hasn't experienced any other symptoms, what actions would you recommend they take?\n" +
                    "\n" +
                    "gupta: dr. fauci?\n" +
                    "\n" +
                    "fauci: well, you know, the idea of having an impediment of the sense of smell is not really unique to any particular viral disease. there are a number of other upper respiratory viral diseases in which that occurs.\n" +
                    "\n" +
                    "there have been some reports that that's an early sign. there really isn't much you can do about it, but it could be a red flag that if you have other symptoms that you're not sure what they are, and you have a decrease in the sense of smell, you might want to think that's a possibility that it's an early sign of coronavirus disease. so that's something that's been well recognized with other viruses.\n" +
                    "\n" +
                    "cooper: our next question comes from larry heyer in charlotte, north carolina.\n" +
                    "\n" +
                    "larry heyer: is there any mounting data on the effects of spring and summer temperatures and its expected impact on coronavirus and its transmission? thank you.\n" +
                    "\n" +
                    "cooper: dr. fauci, that was the hope early on.\n" +
                    "\n" +
                    "fauci: there's no mounting data -- yes, well, right now, i mean, the idea, the concept that when you're dealing with a respiratory-borne virus, that when you get from the cold to the warm weather, there's a diminution in spread, that is not unreasonable, because we see that with influenza and we see that with some of the benign coronaviruses, not obviously the novel coronaviruses.\n" +
                    "\n" +
                    "we are hoping, though it may not happen, that we will see that impact of warmer weather on bringing the infection rate down. but you can't guarantee it because this is a brand-new virus and it may not act like some of the other respiratory viruses in which often you do see a diminution as the weather gets warmer. there's no guarantee that we're going to see that right now.\n" +
                    "\n" +
                    "gupta: and if we do, that also means that, as the weather gets cooler again in the fall and winter, it could come back. is that right, dr. fauci?\n" +
                    "\n" +
                    "fauci: that's exactly right, anderson. and that's the reason why, at the white house press conference today, i emphasized the importance to push ahead with the development of a vaccine and the development of drugs proven by randomized controlled trial. because if that happens, you likely will see a cycling, a seasonal cycling. i would not be surprised, given the efficiency with which this virus spreads, that we will see a cycle.\n" +
                    "\n" +
                    "gupta: let's get to another viewer question, dr. fauci. louis carson in mansfield, texas, sent in this video. take a look.\n" +
                    "\n" +
                    "louis carson: what should -- or could blood transfusions from recovered coronavirus patients be used to potentially aid in the recovery of currently known coronavirus patients? thank you.\n" +
                    "\n" +
                    "gupta: dr. fauci, i think that's a convalescent serum, right, i think he's talking about, he's asking about.\n" +
                    "\n" +
                    "fauci: yes. right. right, that's exactly correct. and, in fact, it would not be the first disease in which you've actually had some success with this. there are a couple of studies going on. one is convalescent sera. the other is the immune globulin from that sera. and the other is the development of monoclonal antibodies from an individual who might actually have recovered, where you actually can clone those b cells and get an unlimited amount of supply. the concept of passive transfer of antibodies is a sound concept that deserves a very serious clinical trial.\n" +
                    "\n" +
                    "cooper: this next one is from emily mitchell in charlotte, north carolina. let's take a look.\n" +
                    "\n" +
                    "emily mitchell: i'm 25 and live in north carolina. my parents are in their 60s and live in virginia. i recently flew on a plane domestically and am still getting over a sinus infection that i had a few weeks ago. they are really insistent on me going home to be with them for this quarantine, but i'm really worried about if i'm a carrier and just don't know it yet. what advice do you have about me going home? and also how would you recommend talking to older individuals who might not want to think that they're at such a risk?\n" +
                    "\n" +
                    "fauci: well, there are a couple of questions there. i mean, first of all, if you have reason to believe that you've been exposed to someone who is infected, then you really should do what we recommend of virtually anybody. what you should do is that you should isolate yourself for up to 14 days. call a physician. see if you want to get tested. and if a testing is available, get tested.\n" +
                    "\n" +
                    "it sounds like you are at a low risk, from what i just heard, but again, i'd want to hear a little bit more about it.\n" +
                    "\n" +
                    "with regard to elderly parents, i think you've got to emphasize, we have a responsibility in society to protect the vulnerable. and those are the elderly, particularly those with underlying conditions. so if you go home and you have any idea that you might have been exposed, you've got to isolate yourself from your parents.\n" +
                    "\n" +
                    "[20:30:06] and, in fact, the recommendations now, particularly in areas where there is a lot of infection, that individuals who are elderly or with chronic conditions that are compromising them, that they should self- isolate and get them away from any possible exposure to someone who might be infected.\n" +
                    "\n" +
                    "gupta: so, regardless of whether she was sick or not, probably at this time, at least for the next few weeks, not visit, you're saying? a lot of people out there thinking about visiting their parents.\n" +
                    "\n" +
                    "fauci: yes, exactly. that's exactly what i meant.\n" +
                    "\n" +
                    "i mean, we have -- i mean, you're getting on a plane. right now, depending upon where you are, you're getting on a plane -- i don't want to worry a lot of people that get on planes. but to the extent possible, you should avoid anything but necessary travel.\n" +
                    "\n" +
                    "now, obviously, you love your parents, and you want to be with your parents. but just think, it might be better now to separate yourself from your parents, so you don't put them at risk.\n" +
                    "\n" +
                    "gupta: good point, dr. fauci.\n" +
                    "\n" +
                    "got another question.\n" +
                    "\n" +
                    "sayli in cranbury, new jersey, sent in this video.\n" +
                    "\n" +
                    "sayli sonsurkar, high school student: hi. i'm a senior high school student from plainsboro, new jersey.\n" +
                    "\n" +
                    "my school campus has recently closed. and we have moved to online learning because of the current coronavirus pandemic.\n" +
                    "\n" +
                    "i wish to become a physician when i'm older. i was wondering what i and others like me can do to help medical personnel and others in need that are currently working tirelessly to save people from this virus.\n" +
                    "\n" +
                    "gupta: need some help, dr. fauci?\n" +
                    "\n" +
                    "fauci: well, that...\n" +
                    "\n" +
                    "(laughter)\n" +
                    "\n" +
                    "fauci: yes, i sure can.\n" +
                    "\n" +
                    "(laughter)\n" +
                    "\n" +
                    "fauci: i'll give you some work, so i can get some more sleep.\n" +
                    "\n" +
                    "(laughter)\n" +
                    "\n" +
                    "fauci: no, i think there are many ways in -- there are many ways in which people like yourself, who are altruistic and want to be part of the solution, that you can volunteer.\n" +
                    "\n" +
                    "whether you do that from faith-based services or what you do, i believe there's a lot of opportunity for people like yourself, who want to contribute, to volunteer.\n" +
                    "\n" +
                    "i mean, the easiest way to do that, i can see it through community services or even faith-based services.\n" +
                    "\n" +
                    "cooper: dr. fauci, as always, we appreciate your time. we know how busy you are.\n" +
                    "\n" +
                    "please keep doing what you're doing. thank you.\n" +
                    "\n" +
                    "gupta: get some rest.\n" +
                    "\n" +
                    "cooper: yes.\n" +
                    "\n" +
                    "fauci: good to be with you, anderson and sanjay.\n" +
                    "\n" +
                    "cooper: just ahead -- just ahead: what the chairman of the federal reserve says about a recession, and your questions about the frightening economic impact of the pandemic.\n" +
                    "\n" +
                    "(commercial break)\n" +
                    "\n" +
                    "[20:36:07]\n" +
                    "\n" +
                    "cooper: and welcome back to our fourth coronavirus town hall.\n" +
                    "\n" +
                    "you still have our social media scroll at the bottom of the screen. you can tweet your questions with #cnntownhall. you can also leave a comment on the cnn facebook page.\n" +
                    "\n" +
                    "the clearest signal of the crushing economic impact of the virus came today, when initial claims for unemployment ballooned to more than three million. that's the highest initial claims in the nation's history.\n" +
                    "\n" +
                    "so, just think about that for a moment, highest in the history of the country in a week's time. and the chairman of the federal reserve says the united states may already be in a recession.\n" +
                    "\n" +
                    "joining us now, cnn's business editor at large, richard quest. he's ready to answer your questions.\n" +
                    "\n" +
                    "so, richard, i mean, hard to underscore the economic fallout that's already occurred because of this.\n" +
                    "\n" +
                    "richard quest, cnn business editor at large: absolutely.\n" +
                    "\n" +
                    "that 3.3 million number tells its own tale. and it could get a great deal worse as this rolls across the rest of the country.\n" +
                    "\n" +
                    "i was looking at some economic forecasts. j.p. morgan think that unemployment will top out at about 8.5 percent. remember, we ended last year at 3.5 percent. oxford economics say it could be 10 percent that will be unemployed.\n" +
                    "\n" +
                    "but it's -- although this is horrific, you have to remember why it's happened. it's because the economy has been artificially paused. so, a lot of these jobs will come back when the economy gets going again.\n" +
                    "\n" +
                    "the issue, of course, is that the economy needs to stay paused while the virus dissipates and is got rid of. if the virus is allowed to incubate and continues -- and you were saying, sanjay, it's called the virus economy -- the significance here is that the social distancing must be maintained. and even though the economic numbers may look dreadful, bordering on disastrous, they will repair and recover quicker as a result of what's being done.\n" +
                    "\n" +
                    "gupta: and we will get to ask bill gates as well, richard, a little bit about some of these things, his perspective.\n" +
                    "\n" +
                    "and i'll preface this question by saying, as doctors, we know nothing about money. so, i will put that out there.\n" +
                    "\n" +
                    "but fed chief jerome powell, he did say that -- quote -- \"we may well be in a recession.\"\n" +
                    "\n" +
                    "how long do you foresee the economy, then, being affected, richard, given all that you just said?\n" +
                    "\n" +
                    "quest: he's being polite. he knows full well that the economy is in recession, in sense of -- how long it will be, go back to 2008, and it was four quarters that the economy was in recession.\n" +
                    "\n" +
                    "but we don't expect anything like that this time round, for the simple reason the economy has been artificially stopped, and it will be started up again hopefully in the second quarter at some point.\n" +
                    "\n" +
                    "and that's the point. by q3, you should be seeing a recovery. by q4, according to ben bernanke and jay powell, the recovery could be robust.\n" +
                    "\n" +
                    "now, that sounds heartless when you think about the fact there will be many businesses that will fail as a result of all of this, thousands, tens of thousands. but for most people in work, when their companies restart, they will be taken back in again, the economy will get back up and running again. and i think, by next year, by the beginning of next year, you will be looking at good growth.\n" +
                    "\n" +
                    "cooper: but, as you said, it all depends on the medical front.\n" +
                    "\n" +
                    "quest: completely.\n" +
                    "\n" +
                    "cooper: we just heard from dr. fauci this could become a seasonal thing. we don't know the impact of that.\n" +
                    "\n" +
                    "quest: completely.\n" +
                    "\n" +
                    "(crosstalk)\n" +
                    "\n" +
                    "cooper: yes.\n" +
                    "\n" +
                    "we got a lot of questions from viewers.\n" +
                    "\n" +
                    "quest: yes. cooper: the stimulus package making its way through congress, cheryl\n" +
                    "\n" +
                    "o'brien (ph) wants to know: \"is there some kind of relief in the package for independent contractors? i currently do not have work because of the shutdown. i cannot receive unemployment. is there any help in the stimulus package?\" she wants to know.\n" +
                    "\n" +
                    "quest: i spent a good half-hour going through the stimulus package to find exactly, chapter and verse, for you, cheryl.\n" +
                    "\n" +
                    "and, yes, in general, during the period, individuals who operate under a sole partnership or as an independent contractor shall be eligible for a covered loan.\n" +
                    "\n" +
                    "now, it's not a grant. it's going to be a loan. and from what i have been hearing from people trying to get in touch with authorities, it's -- there's a lot of bureaucracy getting through.\n" +
                    "\n" +
                    "[20:40:01]\n" +
                    "\n" +
                    "but, yes, cheryl, sole proprietors and independent contractors are covered as if they were employees.\n" +
                    "\n" +
                    "gupta: and, richard, james racik (ph) has another question for you.\n" +
                    "\n" +
                    "it says: \"for those of us who will be receiving a government stimulus check, but are less affected by the virus, where can we donate our checks in order to help support our national medical efforts?\"\n" +
                    "\n" +
                    "fantastic question. love it.\n" +
                    "\n" +
                    "do you know anything about that, richard?\n" +
                    "\n" +
                    "quest: i do.\n" +
                    "\n" +
                    "there's -- obviously, besides your normal faith-based organizations in your own area, which i'm sure would happily take the money, because they're helping local people, the big companies -- like, spotify is going to match up to $10 million.\n" +
                    "\n" +
                    "facebook has said it will match. yelp has said it will match, but also impact your world. cnn has compiled a list of places where you can find details of where you can help. so, from the big to the small, right down to your church, synagogue or temple on the corner, they will take your money.\n" +
                    "\n" +
                    "cooper: richard, just internationally, i mean, what are countries doing to try to relieve some of the fallout in their countries? are there lessons the u.s. can take from what some of these other countries may be doing?\n" +
                    "\n" +
                    "quest: every country is doing something of a major proportion.\n" +
                    "\n" +
                    "germany's probably near the top by the sheer amount of percentage of gdp. it's giving loans. it's giving help in industry, the whole lot.\n" +
                    "\n" +
                    "the united kingdom has agreed to pay 80 percent of wages for employees that will be laid off. and, today, it said it would do the same for the self-employed.\n" +
                    "\n" +
                    "the u.s., at 10 percent of gdp, is on the low side for the moment, but nobody expects it will remain there overall. the biggest tragedy of all is that there's no coordination. and that's because the g20 continues to just talk about what it should be doing, and says something should be done, but doesn't seem very quick to be able to do it.\n" +
                    "\n" +
                    "no, governments around the world are responding. it's an average, an average of between, say, 8 and 12 percent of gdp at the moment. there's more to come.\n" +
                    "\n" +
                    "cooper: richard quest -- richard, thanks very much.\n" +
                    "\n" +
                    "want to bring in leana wen more -- for more of your questions. she's an emergency physician -- emergency room physician, baltimore's former health commissioner, and a veteran of our town halls as well.\n" +
                    "\n" +
                    "dr. wen, at this moment, what's the biggest point that you want all the people watching tonight to be aware of?\n" +
                    "\n" +
                    "dr. leana wen, emergency room physician: i want everyone to know that there is something that we can do right now to slow the spread of coronavirus, that we're not powerless.\n" +
                    "\n" +
                    "as we have been talking about, anderson, we're not powerless against this virus. we can reduce the spread of covid-19 by taking simple steps, physical distancing, social distancing, and staying at home as much as we can.\n" +
                    "\n" +
                    "taking care of ourselves, taking care of our loved ones is also our best defense to protect the community as well.\n" +
                    "\n" +
                    "cooper: let's get some viewer questions.\n" +
                    "\n" +
                    "this one is sent in from lin in ohio.\n" +
                    "\n" +
                    "she wants to know: \"we're a family of four. myself and two children are staying home with no outside exposure. my husband is still going to work. he showers as soon as he comes home from work. is it safe for him to hug our children?\"\n" +
                    "\n" +
                    "dr. wen?\n" +
                    "\n" +
                    "wen: a lot of families are in this position, where somebody does have to go to work. maybe they're a health care worker, a police officer. they're around a lot of people and don't have the option of staying at home.\n" +
                    "\n" +
                    "we can reduce our risk. we can't eliminate every risk. but i would recommend that, while the husband is at work, that he takes every precaution, staying away from people when possible, washing hands, practicing good hand and face hygiene.\n" +
                    "\n" +
                    "when coming home, definitely take off clothes, shower, et cetera. and then go hug and play with the kids. and i think it's another reminder for all of us who can stay at home\n" +
                    "\n" +
                    "that we should stay at home, because there are so many who cannot.\n" +
                    "\n" +
                    "cooper: sanjay, this is a question that charles sent in from hawaii.\n" +
                    "\n" +
                    "it reads: \"for those who contract\" -- or \"contract, but do not get ill from the virus, will tests show you were once exposed to it?\"\n" +
                    "\n" +
                    "gupta: yes, that's a really important question.\n" +
                    "\n" +
                    "this is -- it's called serology testing or antibody testing. basically, it's a little bit of what dr. fauci was talking about. after you have the infection, your body reacts to it, makes these antibodies. and those antibodies can sort of be a signature that you were -- you were previously infected.\n" +
                    "\n" +
                    "and you can test for that. and, as dr. fauci was talking about, maybe you could even use some of the plasma from someone who has these antibodies and inject that into somebody else to help treat their coronavirus infection.\n" +
                    "\n" +
                    "so, that's all sort of sometime away still. but the answer is, yes, you can test for that sort of thing.\n" +
                    "\n" +
                    "cooper: dr. wen, if you get the virus, and then you recover from it, you don't go to the hospital, you don't end up actually getting tested, will you -- and assuming there is immunity to this, that you are then immune, is there any way to know if you had the virus, and you are therefore immune, for the -- just for the future, i mean, down the road?\n" +
                    "\n" +
                    "wen: that's such a great question. and this is what we want to get to. we don't have that test yet. it's being developed. but that would be great. it would be so helpful for people to know whether they have immunity.\n" +
                    "\n" +
                    "[20:45:00]\n" +
                    "\n" +
                    "cooper: and do we know how long immunity lasts for?\n" +
                    "\n" +
                    "wen: we don't know yet. there are some people who speculate that one might have immunity for a long time, even forever, but we just don't know that yet.\n" +
                    "\n" +
                    "cooper: dr. wen and sanjay, this is a question from charmaine in jacksonville, florida. it reads, \"if there is no antibacterial soap available, can plain soap and water still be as effective?\" sanjay?\n" +
                    "\n" +
                    "gupta: yes, you know, so this is a good question. i mean, you know, just soap and water can be very effective, even more effective than the hand sanitizers. you do it for a certain technique. and there's been all kinds of studies on this. so it is really as -- you know, in the midst of a pandemic, it sounds silly to keep talking about hand washing, but it makes a difference and it's really withstood the test of time. cooper: all right, sanjay, along those lines, we got tons of questions\n" +
                    "\n" +
                    "on the proper way to wash your hands and we convinced you to do a tutorial on exactly that, because you have changed the way i wash my hands, i will say. i never knew the back of the hand, in between the fingers and the thumb thing. let's take a look at your tutorial.\n" +
                    "\n" +
                    "(begin video clip)\n" +
                    "\n" +
                    "gupta: all right. i'm going to show you how to wash your hands. most people know how to do this, but always worth remembering. just going to wet my hands here, get plenty of soap, and just start rubbing your hands. and make sure you really interlace your fingers like this and then also turn your hands over. don't forget the backs of your hands. people often forget that. both sides, get it really well.\n" +
                    "\n" +
                    "and then i'll actually get underneath the fingertips here and even the nails a little bit to make sure you clean underneath there. and then you've got to get the thumbs. thumbs are really important. sing the \"happy birthday\" song twice to yourself and that usually will do it. and then i just sort of get the soap off my hands here. and there. and then here's the key. before you turn the water off, dry your hands, and use the same paper towel to turn off the water. you don't contaminate yourself.\n" +
                    "\n" +
                    "(end video clip)\n" +
                    "\n" +
                    "cooper: classic.\n" +
                    "\n" +
                    "gupta: i think i first taught you that in central africa. didn't i?\n" +
                    "\n" +
                    "cooper: you did, you did, yes. the \"happy birthday\" song, which i sang several times while you were doing that demonstration. i think you did about two or three of them.\n" +
                    "\n" +
                    "gupta: yes, it was just a demonstration.\n" +
                    "\n" +
                    "cooper: but, yes, you -- every time i wash my hands, i think of you, actually.\n" +
                    "\n" +
                    "gupta: i do just want to point out that typically faucets nowadays actually the water comes on and off. there are sort of sensors there. so i wouldn't typically waste that much water. but just for the demonstration, i wanted to show people. so i hope that's helpful. it's really important.\n" +
                    "\n" +
                    "cooper: it is. yes.\n" +
                    "\n" +
                    "gupta: let's get to another question here. this is a question sent in from julia in arlington, virginia, which reads, \"does a past history of pneumonia and lung scarring put me at increased risk of severe covid illness? i'm 39 and healthy, but i had pneumonia several times as a child and have some permanent scarring from it that's visible on x-rays.\n" +
                    "\n" +
                    "dr. wen, what do you think? wen: a lot of people are asking questions about this, because the\n" +
                    "\n" +
                    "studies show that those who are more likely to have severe infections are those who are older or have chronic medical conditions. but it's unclear what exactly counts as a chronic medical condition.\n" +
                    "\n" +
                    "some things are very clear, but some things are not. and having pneumonia earlier and having some scarring, unclear whether that's something that predisposes you to having worse coronavirus. but it's always good to act out of an abundance of caution and take extra precautions, including using sanjay's great handwashing video.\n" +
                    "\n" +
                    "cooper: sanjay, grace burke in broken arrow, oklahoma, sent in this video. let's take a look.\n" +
                    "\n" +
                    "grace burke: my mom is 91 years old and lives in her own home and is isolated. how long do i have to self-quarantine? and what does that self-quarantine look like in order to visit her and her sister in her home?\n" +
                    "\n" +
                    "gupta: this is a tough question, right? dr. fauci talked about this. we want to visit our parents, we want to spend time with them. for right now, someone who's 91 years old, if they have any pre-existing conditions -- dr. wen, you tell me if i agree -- i think it's best to probably give some time.\n" +
                    "\n" +
                    "now, if you are living with the person, establishing a sort of space within the home where someone can quarantine themselves or sort of be away and really be able to keep that six-feet distance, use your own utensils, try and have your own space as much as possible. but i say that knowing full well that can be challenging.\n" +
                    "\n" +
                    "cooper: and also, dr. wen, i mean, there's other considerations, too. i mean, if you have a 91-year-old mother who's living alone, there's, you know, potential danger to her just in general of, you know, if she's going out grocery shopping by herself, all those sorts of things.\n" +
                    "\n" +
                    "wen: that's right. and it's weighing a lot of different risks. and i think it has to depend on the health of everyone involved, the likely exposures, and also just -- there is this human element, too, that we want to be with our families. so it's a tough decision that a lot of people have to be making right now.\n" +
                    "\n" +
                    "gupta: you know, i've got to say, as well -- i think, anderson, you and i have talked about it. but i was supposed to visit my parents who are in their late 70s sort of i think -- you know, end of february, early march and, you know, decided not to do that. and they really wanted us, especially my kids, to come visit. we couldn't do that.\n" +
                    "\n" +
                    "[20:55:02]\n" +
                    "\n" +
                    "but we've been calling a lot and facetiming a lot. and keep coming back to this idea that social distancing does not have to mean social isolation, especially with technology. it's not ideal, but, you know, in some ways, it can still keep you pretty connected.\n" +
                    "\n" +
                    "cooper: yes, it's so hard. i mean, it's such a difficult thing to try to weigh all the competing factors.\n" +
                    "\n" +
                    "sanjay, aditi in canada sent in this video. let's take a look.\n" +
                    "\n" +
                    "aditi: are babies at high risk for coronavirus? and what symptoms should parents look for in babies, because they cannot explain what is happening to them? thank you.\n" +
                    "\n" +
                    "gupta: yes, you know, it's a good point. i'll give you some data, but just keep in mind that it can be very individualized. you know, the elderly and people with pre-existing conditions are the ones that are most likely to become either seriously or critically ill. you know, some 18 percent to 20 percent of people in those categories develop severe or critical illness.\n" +
                    "\n" +
                    "for younger people, it's a lot lower. it's closer to 5 percent or 6 percent. but interestingly for babies, 0- to 1-year-old, it was closer to 11 percent, so about 1 in 10 roughly.\n" +
                    "\n" +
                    "babies can't talk, as our viewer just mentioned. you've got to monitor things. you have to, you know, check fever, you know, check if there's any abnormal vital signs, you know. and if there's a question, especially with the baby, you know, call your doctor and make sure you call ahead before just showing up at the er.\n" +
                    "\n" +
                    "cooper: yes. i should point out to our viewers, bill gates is coming up in just a few minutes. we're going to be talking to him for about -- for an extended period of time. he's really been out front on this, warning about the risk of a pandemic like this.\n" +
                    "\n" +
                    "dr. wen, you've talked about this on the air, so i'm not saying anything, i'm not revealing anything, that you are pregnant. what kind of precautions are you taking? and also now, you know, a lot of hospitals in new york and i assume elsewhere, although i'm not sure i know in new york, are not allowing loved ones inside the delivery room out of concern for the mother and health care workers, and obviously for the baby, as well.\n" +
                    "\n" +
                    "wen: that's right. so i'm now 39 weeks pregnant, and so on baby watch. it could happen any day now. and definitely it's something that i think about a lot, about the potential risk of contracting coronavirus and what that might look like in pregnancy.\n" +
                    "\n" +
                    "now, it does not appear that there is an elevated risk to pregnant women of having severe symptoms or severe effects because of pregnancy. that's the good news. but there is so much that's not known about the risk of coronavirus in pregnancy. and pregnant women should take extra precautions because we are medically vulnerable.\n" +
                    "\n" +
                    "now, these guidelines are changing every day. my hospital used to welcome the entire family in the delivery room. now there's only one person that's allowed. and you mentioned, anderson, about new york hospitals, some of them only allowing -- were not allowing any visitors at all. and i can't really imagine not having my husband or any support system there.\n" +
                    "\n" +
                    "but i think this is such an extraordinary time for all of us. and we're all living through this level of uncertainty. and i think about the sacrifices that so many people are making, and i think it's something that i might have to deal with myself, about having instead of face-to-face time including face-to-face time with my loved ones afterwards with the baby, having facetime instead.\n" +
                    "\n" +
                    "cooper: sanjay, i was just looking on the bottom of the screen. one of the questions a viewer wrote in just now, tweeted in, was, is it safe to ride the subway in new york if the car isn't very crowded?\n" +
                    "\n" +
                    "gupta: you know, it's challenging, i think. i mean, i think if you're very careful and you can really maintain a social distance and be careful of surfaces and...\n" +
                    "\n" +
                    "cooper: i mean, that's the problem. you hold onto the handles and stuff on the subway.\n" +
                    "\n" +
                    "gupta: yes, i mean, look, if it's essential -- and, again, i know for some people this is essential, so i don't want to be dismissive in any way of this. but it's challenging. i mean, i think it really has created a significant awareness for everybody, how you live your life.\n" +
                    "\n" +
                    "i mean, i just notice myself moving much more slowly, being more mindful even as i touch surfaces around me. if you can do that and take this seriously, i think it's possible. but we're still in this very much, anderson, so a few weeks from now, that answer may be different.\n" +
                    "\n" +
                    "cooper: yes, dr. wen, thanks so much. sanjay and i are sticking around. our conversation with bill gates is coming up in just a few minutes. as we said at the top, the bill and melinda gates foundation, they -- early on they spent $100 million -- they gave $100 million to coronavirus relief. bill gates has sounded the alarm for years about a pandemic. now that it's here, his thoughts about how to stop it.\n" +
                    "\n" +
                    "(commercial break)\n" +
                    "\n" +
                    "[20:58:25]\n" +
                    "\n" +
                    "cooper: and welcome back to cnn's global town hall, our fourth global town hall. this hour, our medical experts are going to answer your questions about the toll the pandemic has taken on people's mental health. we'll also talk to olympic swimmer katie ledecky, a five-time gold medalist, one of the most dominant swimmers in the world today, about the cancellation of this year's games.\n" +
                    "\n" +
                    "want to start, though, with our guest for the next half-hour, bill gates. mr. gates and his wife, melinda, have been tireless advocates for the poor through their bill and melinda gates foundation. they early on gave out $100 million toward efforts around the world to control the coronavirus.\n" +
                    "\n" +
                    "gupta: and bill gates has also been warning about the threat of a pandemic for years. in 2015, he gave this ted talk saying that the greatest risk of a global catastrophe wouldn't come from nuclear war. it would come from a highly infectious virus. and he said, quote, \"if anything kills over 10 million people in the next few decades, it's most likely to be a highly infectious virus, rather than a war.\"\n" +
                    "\n" +
                    "welcome now to you, mr. gates. thanks so much for joining us.\n" +
                    "\n" +
                    "bill gates, co-chair, bill & melinda gates foundation: good to talk to you.\n" +
                    "\n" +
                    "cooper: bill, in that ted talk, you pointed out that we invest a lot in being ready for a war or nuclear deterrence, but you said, quote, \"we've invested very little in a system to stop an epidemic. we're not ready for the next epidemic.\"\n" +
                    "\n" +
                    "just big picture, before we get into details, how do you see the coronavirus, where we're at right now in the united states, compared to other pandemics the world has faced? i mean, what's the good news, what's the bad news?\n" +
                    "\n" +
                    "gates: well, this is a terrible pandemic. because it's spread human to human in a respiratory way, you can infect somebody when you're still fairly healthy. and there are many things, like ebola, that aren't like that. you're flat on your back before you become significantly infectious.\n" +
                    "\n";


            TrumpText = "the president:  hello, everybody.  hello.  thank you very much.  thank you.  beautiful day.  very good what you’re doing.  look at all those empty seats.  never seen it like that.  oh, boy.  well, how the world has changed.  how the world has changed, right?  but it’s going to end up being better than ever.\n" +
                    "i want to thank you very much for being here.  and i’d like to update you on the steps we’re taking on our ongoing fight to defeat the virus.\n" +
                    "this morning at 7:55, i spoke to the leaders of the g20.  had a great meeting.  and we have a lot of different ideas, a lot of good ideas.  we’re working together.  the leaders gathered virtually around the world to discuss the whole subject of the problem that, right now, 151 nations have got.  we had:\n" +
                    "•    president alberto fernández of argentina\n" +
                    "•    prime minister scott morrison of australia\n" +
                    "•    president jair bolsonaro of brazil\n" +
                    "•    prime minister justin trudeau of canada\n" +
                    "•    president xi of china\n" +
                    "•    president emmanuel macron of france\n" +
                    "•    chancellor angela merkel of germany\n" +
                    "•    prime minister modi of india\n" +
                    "•    president widodo of indonesia\n" +
                    "•    prime minister giuseppe conte of italy\n" +
                    "•    prime minister shinzō abe of japan.  congratulations to japan on making a great decision on the olympics.  going to make it next year, 2021.\n" +
                    "•    president andrés manuel lópez obrador of mexico.  i want to thank the president of mexico for having done such a great job with respect to the military.  we have 27,000 mexican soldiers on our southern border, and very few people are getting through — i can tell you that.  and we got to keep it that way.  and we have a great relationship with mexico now.\n" +
                    "•    president putin of russia\n" +
                    "•    king salman of saudi arabia\n" +
                    "•    president ramaphosa of south africa\n" +
                    "•    president moon of, as you know, a country that we spend a lot of time in: south korea.  we’re working very hard on that.\n" +
                    "•    prime minister sánchez of spain\n" +
                    "•    president erdoğan of turkey\n" +
                    "•    prime minister boris johnson of the united kingdom\n" +
                    "•    president of the european commission ursula von der leyen\n" +
                    "•    president of the european council charles michel\n" +
                    "•    united nations secretary-general antónio guterres\n" +
                    "•    world health organization director tedros adhanom\n" +
                    "•    world bank president david malpass\n" +
                    "•    and international monetary fund managing director kristalina georgieva\n" +
                    "so, that’s a big group but it’s a great group.  it’s — and they were all there — every one of them.  and we talked about the problem.  and hopefully it won’t be a problem for too much longer.\n" +
                    "the united states is working with our friends and partners around the world to stop the spread of the virus and coordinate our efforts.  we discussed how vitally important it is for all of our nations to immediately share information and data — and we’ve been doing that, to a large extent, but we’ll do it even more so — and to inform our — i guess you could say inform each of us on the fight that we’ve got going one way or the other.  it’s a little bit different, but we’re handling it a little bit in different ways.\n" +
                    "but there is great uniformity, i think.  we had a — it was a terrific meeting.  tremendous spirit among all of those countries.  you had 20 countries plus the other people that i mentioned.  and tremendous spirit to get this over with.\n" +
                    "after the meeting with the world leaders, i spoke with the governors of our 50 states and territories.  our team has been in constant communication with the governors, and we had a terrific meeting.\n" +
                    "somebody in the fake news said that one of the governors said, “oh, we need tom brady.”  i said, “yeah.”  he meant that in a positive way.  he said, “we need tom brady.  we’re going to do great.”  and he meant it very positively, but they took it differently.  “they think tom brady should be leading the effort.”  that’s only fake news.  and i like tom brady.  spoke to him the other day.  he’s a great guy.\n" +
                    "but i wish the news could be — could be real.  i wish it could be honest.  i wish it weren’t corrupt, but so much of it is.  it’s so sad to see.  just so sad to see.\n" +
                    "we had a great meeting.  i tell you what: i’m sure you have tapes of the meeting.  i’m sure that you were able to get tapes very easily.  so you had 50 governors-plus.  and if you had tapes, you’d see it was really — i mean, there was no contention.  i would say virtually none.  i would say maybe one person that was a little tiny bit of a raising of a voice, a little wise guy, a little bit.  but he’s usually a big wise guy.  not so much anymore.  we saw to it that he wouldn’t be so much anymore.  but he is — we had a — i mean, i would rate — mike was there; a lot of the folks in the back were there.  and it was a — it was a great meeting.  it took place at about 12 o’clock.\n" +
                    "so we went from the g20 to the governors.  we also spoke about the economic relief with the governors and the package that we’re moving through congress to deliver much-needed financial assistance to hardworking families and small businesses.\n" +
                    "i want to thank democrats and republicans in the senate for unanimously passing the largest financial relief package in american history, 96 to 0.  and i have to say it’s the largest, by far, and i’m profoundly grateful that both parties came together to provide relief for american workers and families in this hour of need.\n" +
                    "the house of representatives must now pass this bill, hopefully without delay.  i think it’s got tremendous support.  when you’re at 96 to nothing — and, as you know, a couple of those people are quarantined, and one — rand paul — is — he’s actually got it.  but he’ll — he’ll be better.  he’s been a great guy.  he’s been a great friend of mine, actually.\n" +
                    "the massive $2.2 trillion relief package includes:\n" +
                    "•    job retention loans for small businesses with loan forgiveness available for businesses that keep their workers on the payroll.  that’s pretty good.  loan forgiveness — keep the workers on the payroll.  that’s pretty good.\n" +
                    "•    direct cash payments will be available to american citizens earning less than $99,000 per year; $3,400 for the typical family of four.\n" +
                    "•    expanded unemployment benefits.  the average worker who has lost his or her job will receive 100 percent of their salary for up to four full months.\n" +
                    "these are things that — by the way, we have plenty more to go and — but they’re things that nobody has ever had any package like this done.  and i just want to thank them.  hopefully, it’ll get approved equally easily in the house, really.  i think it will go through pretty well.  from what i hear, virtually everybody.\n" +
                    "•    critical support for the hardest-hit industries with a ban on corporate stock buybacks and tough new safeguards to prevent executive compensation abuse.\n" +
                    "•    over $100 billion for our amazing doctors, nurses, and hospitals.\n" +
                    "•    $45 billion for the disaster relief fund, more than doubling the amount available.  this is tremendous stuff.\n" +
                    "•    $27 billion for the coronavirus response, including $16 billion to build up the strategic national stockpile with critical supplies including masks, respirators, and all sorts of pharmaceuticals.\n" +
                    "•    $3.5 billion to expand assistance to childcare providers and childcare benefits to healthcare workers, first responders, and others on the frontlines of the crisis.  and these are really brave, incredible people, i have to say.  and some of them are getting sick, and some of them are getting very sick, and some of them don’t even recover.  they’re incredible people.\n" +
                    "•    $1 billion for defense production act procurement.  we are, as you know, using the act, but we use it only when necessary.     we use it as leverage.  we generally don’t have to use it to accomplish what we want to accomplish.\n" +
                    "as of today, fema has shipped over 9 million n95 masks, 20 million face masks, 3.1 million face shields, nearly 6,000 ventilators, 2.6 million gowns, 14.6 million gloves.  and we’re sending more every day, and we’ve got tremendous amounts of equipment coming in.\n" +
                    "a lot of great companies are making equipment right now.  the ventilators, obviously they take a little longer to make, but we have a lot of companies making them.  and we’re going to be in great shape.\n" +
                    "we took over an empty shelf.  we took over a very depleted place, in a lot of ways.  as you know, the testing is going very, very well.  and that was obsolete and broken, and we fixed it and it’s been going really good.\n" +
                    "and i think, very importantly, the stockpile — we’re really filling it up, and we fill it up rapidly, but we get it out.  sometimes we have it sent directly to the states instead.\n" +
                    "and again, the state has to be doing this kind of a thing also.  we’re sort of a — we look — we look from behind a little bit and we look at how are they doing, and if they need help, we do it. but it’s their first responsibility.  sometimes they just can’t get it, but we load it up and we send it out.  but if we can, we have it sent directly to the state.  we want it to go directly to the point where we want it.\n" +
                    "i can now announce something that i think is incredible, what they’ve done in the navy, because the incredible naval hospital ship the usns comfort — which is incredible, actually, when you see it inside — will be underway to new york city on saturday.\n" +
                    "so it’s going to be leaving on saturday, rather than three weeks from now.  they did the maintenance quickly, and it was going to be there for quite a while longer — another three or four weeks.  and it should be arriving — i told the governor 20 minutes ago, governor cuomo, that the ship will be arriving in new york harbor on monday.\n" +
                    "i think i’m going to go out and i’ll kiss it goodbye.  i’ll go — i’ll go to — it’s in virginia, as you know.  and i will go and we’ll be waving together because i suspect the media will be following.  john, are you going to be following?  maybe.  you never know.\n" +
                    "q\ti always follow the comfort, sir.  it’s a very important vessel.\n" +
                    "the president:  it’s a great ship.  it’s a great vessel, is right.  so, if you want to go, i’ll see you there.  and if you don’t, that’s okay.\n" +
                    "after being fully loaded with medical supplies, it’s going to be — it’s loaded up to the top.  and it’s over at the norfolk naval base; that’s where it departs.  it is expected then to — i mean, we’re saving about three to four weeks by the incredible work done by the navy.  and i actually look forward to saturday to see it go.\n" +
                    "the ship will arrive, and i believe it’s going to get a little bit of a ceremony.  there’s something very beautiful about it.  it’s an incredible piece of work.  going to be landing at pier 90 in manhattan to provide hospital surge capacity for the new york metropolitan area.  so it’s a surge capacity.  they may use it for this or they may have other people coming in from hospitals, unrelated to the virus, and then they’ll use those hospitals on land.  they’ll use those hospitals for the virus.\n" +
                    "but we’ll see how they do it.  they could do it either way — one way or the other, whichever one is best.  but it could be — because it’s set up so well for a regular hospital, that they may take people out of hospitals and then use those rooms for the virus.\n" +
                    "the national institute of health and the private sector, working closely with the fda, continue to collaborate to discover and test treatments and therapies that can effectively reduce the duration and symptoms of the virus and help — very much help people to recover.  and i’m firmly committed to bringing these treatments to market very quickly.\n" +
                    "we have a — we have a lot of tests going on with regard to different medicines.  and i hope — i hope we get lucky.  i hope we hit.  a lot of talented scientists and doctors are working on therapeutics, a cure, vaccines.  i think we’re doing very well.  tony may speak to that a little bit later, but i think we’re doing very well with regard to the vaccines.\n" +
                    "i think we’re doing well with regard to a lot of the things i just mentioned, but we’ll have to see what happens.  we’re going to know fairly soon about a lot of them.  but it’s very advanced, and the vaccines are very advanced, prior to, as you know, a fairly reasonably long test period of, in that case, over a year.\n" +
                    "every american should be proud of the incredible spirit our country has brought to this effort.  it’s been incredible.  citizens from all walks of life have come together to turn the tide in this battle.  we’re witnessing the extraordinary power of american unity like a lot of people have never seen.  even getting a vote — you’re talking about trillions of dollars, and you get a vote of 96 to nothing.\n" +
                    "we are waging war on this virus using every financial, scientific, medical, pharmaceutical, and military resource to halt its spread and protect our citizens.  i want to express our tremendous thanks to the american people for continuing to practice social distancing — like you people are practicing right here; i don’t know, this room may never be the same — maintaining good hygiene, and follow government guidelines.\n" +
                    "vice president pence lifts up that card every time.  and it’s not very complicated, but hopefully you can do that.  and your commitment will make all the difference in the world.  and that’s — one of the big ones will be: for a while, stay home.  just relax.  stay home.  we’re making a lot of progress.\n" +
                    "as we continue to gather more information and accelerate the testing — where we’re doing record numbers of tests now, far more than any other country has done.  i told you yesterday: eight days here — because you heard so much about south korea.  the media kept talking, “south korea, south korea.”  we have a great relationship with president moon in south korea.  but when i hear so much about south korea —\n" +
                    "so, in eight days — in eight days, we do more testing than they did in eight weeks.  and it’s a very highly sophisticated test, too.\n" +
                    "we’ll be able to deploy even more data-driven and targeted approaches to slow the — ultimately, you know, it’s a very devastating thing, but we will vanquish this virus.  and it’s — a lot of progress has been made.\n" +
                    "that’s why earlier today i sent a letter to america’s governors, describing how we will be using the data to update existing guidance on social distancing, which will be developed in close coordination with our nation’s public health officials and scientists.\n" +
                    "because of the sacrifices of our great doctors and nurses and healthcare professionals, the brilliance of our scientists and researchers, and the goodness and generosity of our people, i know that we will achieve victory and quickly return to the path of exceptional health, safety, and prosperity for all of our citizens.\n" +
                    "we have to get back to work.  our people want to work.  they want to go back.  they have to go back.  and we’re going to be talking about dates.  we’re going to be talking with a lot of great professionals.  but this is a country that was built on getting it done, and our people want to go back to work.  i’m hearing — i’m hearing it loud and clear from everybody.  so we’ll see what — what happens.  we’re going to have a lot more information early next week, and we’ll be reporting that back.\n" +
                    "but i just want to leave it with you: we have to go back.  this is the united states of america.  they don’t want to sit around and wait.  and they’ll be practicing — and, by the way, a lot of people misinterpret when i say “go back.”  they’re going to be practicing, as much as you can, social distancing and washing your hands and not shaking hands and all of the things that we talk about so much.  but they have to go back to work.  our country has to go back.  our country is based on that.\n" +
                    "and i think it’s going to happen pretty quickly.  i think it’s going to happen pretty quickly.  a lot of progress is made, but we got to go back to work.  we may take sections of our country.  we may take large sections of our country that aren’t so seriously affected, and we may do it that way.  but we’ve got to start the process pretty soon.  so we’ll be talking to you a little bit more about that next week.\n" +
                    "and with that, if you have any questions, you could ask.  and then i’m going to have the vice president stay behind, and he’s going to take questions and also introduce some of the people.  you can ask them some questions.\n" +
                    "john, please.\n" +
                    "q\tmr. president, if i could — unemployment numbers out today: 3.3 million.\n" +
                    "the president:  yeah.\n" +
                    "q\ti take it, not a surprise.\n" +
                    "the president:  no.  not at all.\n" +
                    "q\tbut still a staggering number.\n" +
                    "the president:  oh, sure.\n" +
                    "q\ti’m wondering about your perspective on that.\n" +
                    "the president:  well, it’s nobody’s fault — certainly not in this country.  nobody’s fault.  we got very lucky when we made a decision not to allow people in from china at a very early date.  i say that because some people don’t want to accept it.  but this was a great decision made by our country, or there’s — the numbers that you’re talking about — we’re a big country; they’d be far greater, far bigger.\n" +
                    "so when i heard the number — i mean, i heard it could be 6 million, could be 7 million.  it’s 3.3 or 3.2.  but it’s a lot of jobs.  but i think we’ll come back very strong.  the sooner we get back to work — you know, every day that we stay out, it gets harder to bring it back very quickly.  and our people don’t want to stay out.\n" +
                    "so i know those numbers, john, but i think you’ll see a very fast turnaround once we have a victory over the “hidden enemy,” as i say.  it’s a hidden enemy.  sometimes a hidden enemy is a lot tougher than somebody that stares you in the face, right?  so we’ll see what happens.  but, i mean, they’re fully expected numbers — at least.  i mean, at least.\n" +
                    "steve, please.\n" +
                    "q\tthere’s a u.s. proposal to deploy some troops along the canadian border.\n" +
                    "the president:  yeah.\n" +
                    "q\tand prime minister trudeau is complaining about that.  why is that necessary?\n" +
                    "the president:  well, we have very strong deployments on the southern border, as you know, with mexico.  and we had some troops up in canada.  but i’ll find out about that.  i guess it’s equal justice, to a certain extent.  but, in canada, we have — we do have troops along the border.\n" +
                    "you know, we have a lot of things coming in from canada.  we have trade — some illegal trade that we don’t like.  we have very strong sanctions on some.  we have very strong tariffs on dumping steel.  and we don’t like steel coming through our border that’s been dumped in canada so they can avoid the tariff.\n" +
                    "you know, i charge a lot of tariff for the steel.  and it’s been great for our steel companies because now they can really go — you look at what’s happened with steel.  it’s been pretty incredible.  but we’ve taken in billions and billions of dollars in tariffs on steel, and much of it comes in from china, but they can come through the canadian border, too.  so we’re always watching for that.\n" +
                    "q\tand if i’m reading the numbers correctly, the united states now has surpassed china as the country with the highest number of virus cases.  does this surprise you at all?  is it following a predictable trajectory?\n" +
                    "the president:  no, i think it’s a tribute to our testing.  you know, number one, you don’t know what the numbers are in china.  china tells you numbers, and — i’m speaking to president xi tonight, i believe, and we’ll have a good conversation, i’m sure.  but you just don’t know, you know, what are the numbers.  but i think it’s a tribute to the testing.  we’re testing tremendous numbers of people and every day — the way the system works.\n" +
                    "and i want to thank, especially, roche has been fantastic.  great company.  they’ve done a tremendous amount.  deborah was telling me before that they were really — they’ve really stepped up to the plate and done great, as have other of the companies, but it seems that they’re really doing it particularly well.  so, you know, we’ll see what happens there.\n" +
                    "but it’s a tribute to the amount of testing that we’re doing.  we’re doing tremendous testing.  and i’m sure you’re not able to tell what china is testing or not testing; i think that’s a little hard.\n" +
                    "q\tyeah, mr. president, on the 3.3 jobless claims, you just suggested it’d get 6 to 7 million.  a lot of those workers —\n" +
                    "the president:  no, i didn’t say that.  no, you’re wrong.  i didn’t say that.\n" +
                    "q\tyou thought that —\n" +
                    "the president:  i said some people were projecting that it would be 6 or 7, and it’s, i believe, 3.3.\n" +
                    "q\tit came in at 3.3.\n" +
                    "the president:  yeah.\n" +
                    "q\tmillions of americans out of work.  some of them will be losing their insurance.  what’s your plan to make sure — through no fault of your own, as you just mentioned — that they stay insured?  are you willing to plus up the subsidies for some of the exchanges under obamacare; expand medicaid?  what’s being considered?\n" +
                    "the president:  so — well, i mean, the things i just read to you are being considered and other things are being considered.  people are going to be getting big checks.  and it’s not their fault.  what happened to them is not their fault.\n" +
                    "q\tbut for their health insurance.\n" +
                    "the president:  so we’re doing — we’re doing a lot of different things on health insurance.  we have meetings on it today.  we’re taking care of our people.  this is not their fault what happened, and we’re taking care.  we’re starting off by sending them very big checks.  i think, for a family of four, it’s about $3,000.  and we’re taking care of our people.  we’re taking care of our workers.\n" +
                    "this was not — you know, as i say, this was not a financial crisis; this was a health crisis, a medical crisis.  we’re going to take care of our people.\n" +
                    "please.  yeah.  please.\n" +
                    "q\tthe national restaurant association came out — the national restaurant association —\n" +
                    "the president:  restaurant.\n" +
                    "q\t— came out with a survey this morning, saying that 3 percent of all restaurants in this country have shuttered for good in the past three weeks, and the projection is that 11 percent more are going to close in the next 30 days.\n" +
                    "so what do you say to a restaurant owner who is looking at his sheets and thinks he has to close within the next 30 days?\n" +
                    "the president:  well, i hate to — i know the business very well, i understand the restaurant business.  it’s a very delicate business.  it’s a business that — that — it’s not easy.  you know, i always say, in a restaurant business, you can serve 30 great meals to a person or a family, and they love it; one bad meal — number 31 — they never come back again.  it’s a very tough business.\n" +
                    "but there are great people that run restaurants.  and i’ve heard 3 percent could be lost and you could go as high as 10 or 11 percent, but they’ll all come back in one form or another.  it might be a different restaurant, but it’s going to be a great business for a lot of people.  and we’re making it easy for people to — look, what we’re doing — what we’re doing in terms of loans, what we’re doing in terms of salaries — they’ll all come back.  it may not be the same restaurant, it may not be the same ownership, but they’ll all be back.\n" +
                    "yes, sir.  please.\n" +
                    "q\tsir, you mentioned the pledges from american companies to provide supplies, but is it — does it — as we top 81,000 cases in the u.s., does it make sense to relook at using the defense production act?\n" +
                    "the president:  well, i talked about the defense production act a lot.  and i’ve — you know, i’ve enacted it.  i have it.  i can do it with a pen.  and we have actually used it on two minor occasions and then we could withdraw it.\n" +
                    "but, for the most part, the companies — we don’t need it.  we say, “we need this,” and they say, “don’t bother.  we’re going to do it.”   i mean, we — we’re dealing with ford, general motors, 3m.  we’re dealing with great companies.  they want to do this.  they want to do this.  they’re doing things that — that frankly, they don’t need somebody to walk over there with a — with a hammer and say, “do it.”  they are getting it done.  they’re making tremendous amounts of equipment.  tremendous amounts.\n" +
                    "q\thave they been supportive?\n" +
                    "the president:   and when this is over, we’re going to be fully stockpiled, which they would have never been, except for a circumstance — this was — this was something that nobody has ever thought could happen to this country.  i’m not even blaming — look, we inherited a broken situation, but i don’t totally blame the people that were before me and this administration.  nobody would have ever thought a thing like this could have happened.\n" +
                    "but the production act — defense production act — is a wonderful thing, but i just haven’t had to use it.  they know it’s activated.  they know i can use it.  maybe that frightens them a little bit.  you know, it’s got tremendous power.  but i haven’t had to.\n" +
                    "please.\n" +
                    "q\tthank you, sir.  a question for me and then another question, if you’ll let me, for some of my colleagues who are social distancing (inaudible).\n" +
                    "the president:  go ahead.  where are they?  they’re all outside, trying to get in.  i know.\n" +
                    "q\tfirst question has to do with cruise liners like carnival and royal caribbean.  they want this relief aid, but they’re worried that because they offshored to places like panama and liberia, they might not qualify.  senator hawley has said that they should move back to the united states before they get a check.  do you agree?  should they pay u.s. taxes to get u.s. taxpayer relief?\n" +
                    "the president:  so, i’m a big fan of senator hawley.  and i also like the idea.  there were some senators that didn’t want to do anything like carnival — great company — but they’re based in different places.  i won’t tell you where.  i could tell you exactly where they’re based, but i won’t do that.  but they’re based in actually more than one place, as you know.  ships are registered in different locations.\n" +
                    "i do like the concept of perhaps coming in and registering here, coming into the united states.  it’s — you know, it’s very tough to make a loan to a company when they’re based in a different country.  but, with that being said, they have thousands and thousands of people that work there and, maybe almost as importantly, that work onshore, filling the ships with goods and products.  and the cruise line business is very important.\n" +
                    "and i know carnival, what a great job they do.  micky arison.  and i would think that we could stick with senator hawley and maybe really look at that very seriously.\n" +
                    "look, it’s a big business.  it’s a great business.  it’s a — it’s a business that employs tremendous of number of people, outside of the ship itself.   i mean, you look — you look at these ports.  it’s loaded up with shops and — and people that are involved with the ship.  so we’re going to work very hard on the cruise line business and we’re going to try and work something out, but i like the concept.\n" +
                    "yeah, go ahead.\n" +
                    "q\tthe second one — thank you, sir: the senate bill includes aid that’s directly tied to the airlines.  and since before the pandemic, boeing was already suffering from, you know, the losses of 737 max airplanes.\n" +
                    "the president:  yeah.  sure.\n" +
                    "q\tdo you think it’s appropriate to use this legislation to, sort of, provide them with $17 billion of aid on top of, you know, $25 billion that they could qualify for as a passenger airline and then another $4 billion that they could qualify for as a cargo airline.  is that fair?\n" +
                    "the president:  so, the airline business a very tough business.  over many years, it’s been very, very tough.  it’s got everything.  it’s got labor.  it’s got very strong, powerful — you know, you look at the cost of these airliners.  everything is tough — very highly technological.  you look at how complicated, how complex.  it’s got unions.  it’s got everything.  the airline business, generally speaking, has unions.  it’s a very tough business; always been a very tough business.\n" +
                    "with that being said, we have to keep our airlines going.  and we’re going to be using some — now, maybe we’ll take a piece of the airlines for the country, for our country; where we loan money and we take a piece.  it’s all fully ready.  we’re ready to go.  but if we didn’t do that, we’d end up with no airlines and we can’t do that.\n" +
                    "the airline business is very vital to our country.  it’s a tough business.  we have to understand that.  so, not — i mean, i could tell you other businesses that are different kinds of business.  they’re very good businesses, but airlines have always been very, very tough.\n" +
                    "please.\n" +
                    "q\ttwo questions for you, mr. president.\n" +
                    "the president:  no.  please.\n" +
                    "q\tmr. president, thank you.\n" +
                    "q\ti have two questions for you.\n" +
                    "the president:  yeah.  i didn’t call you, i called this gentleman.\n" +
                    "q\tmr. president, thank you.  thanks a lot.  on monday —\n" +
                    "the president:  who are you with?\n" +
                    "q\t— did you speak with —\n" +
                    "the president:  who are you with?\n" +
                    "q\ti’m with bloomberg.\n" +
                    "the president:  yeah.  bloomberg news.\n" +
                    "q\tmario parker.\n" +
                    "the president:  how’s michael doing?  good?\n" +
                    "q\t(laughs.)  mr. president, on monday, did you speak with chinese president xi before you urged americans to not blame asian americans for the coronavirus?  we noticed that you’ve backed off of that language.  i know you’re speaking with him again tonight.\n" +
                    "the president:  no, i didn’t.  i’m speaking to him tonight.  it’s scheduled to go tonight.  i’ll have a call with president xi of china.  i have a very good relationship.\n" +
                    "no, i didn’t like when they came up.  and it — it wasn’t him.  somebody at a lower level — mid-level — we found out, pretty much.  but they made a statement that our soldiers brought it into china.  no, it came from china.\n" +
                    "and, you know, we just signed a very big deal with china.  they’re paying us a lot of money in tariffs and other things.  they never paid us 10 cents.\n" +
                    "look, china has taken advantage of the united states — until i came here — with sleepy joe biden and obama and bush and everybody else.  i’m not blaming them; i’m blaming everybody.  they were allowed to — $500 billion a year they were taking out.  we had trade deficits that were so large nobody’s ever seen anything like it.\n" +
                    "and we’ve changed it.  look, now we’re taking in billions of dollars.  and we gave some to our farmers because china, you know, they targeted our farmers.  and our farmers are very happy and our farmers got through a very rough period because of what i was able to do — took the money from china and gave it to the farmers — and we had plenty left over after that.\n" +
                    "now we’re going into a phase two negotiation with china.  but we’re getting 25 percent on $250 billion and then we’re getting a lot on money after that.\n" +
                    "so we’ve never had a deal with china.  they — china took advantage of the united states.  and you know what?  i don’t blame china for that.  i blame the people that were right here because they should have never allowed it to happen.  but the relationship with china has been a very good one.\n" +
                    "q\tdid president xi — mr. president, did president xi ask you to — to calm that language down or to not use that language?\n" +
                    "the president:  he never asked me to calm it down, no.  somebody might have spoken to somebody, but nobody spoke to me about it.\n" +
                    "q\tmr. president, earlier —\n" +
                    "the president:  i think it was time though, because, you know, i talk about the chinese virus and — and i mean it.  that’s where it came from.  you know, if you look at ebola, if you look at all — lyme.  right?  lyme, connecticut.  you look at all these different, horrible diseases, they seem to come with a name with the location.  and this was a chinese virus.  but i don’t have to say it, if they feel so strongly about it.  we’ll see.\n" +
                    "but, you know, we have — we just made a great deal with china — great, hopefully, for both parties.  but we’ve made a deal with china and we’re going to do another one, it looks like.  they want to do it very badly.  maybe they want to wait, like iran.  they want to wait to see whether or not trump gets beaten in the election because would they love to negotiate with biden or somebody else other than me.  they would love it.  that’s their best dream in the world.  so many others.\n" +
                    "so, there are some that maybe are, you know, waiting until after november 3rd, the election day.  but i think we’re doing very well.\n" +
                    "it would be sad if we blew all of the advantages that we have right now because we’ve made unbelievable trade deal.  whether it’s mexico, canada, japan, south korea, china, and others, we have — we have changed the whole thing around.\n" +
                    "please.\n" +
                    "q\tmr. president, earlier today, you sent a notification letter to the nation’s governors, saying that you will soon come out with new guidelines about social distancing and other items.  do you have any data yet to suggest which specific areas of the country may have their guidelines relaxed?\n" +
                    "the president:  yeah.\n" +
                    "q\twhich areas of the country may have their guidelines tightened?\n" +
                    "the president:  i think deborah will talk to you about that, and tony, in a few minutes.  but i think we’ll start talking about that.  because we have to open up.  we can’t say, “let’s close.”  the people don’t want to close, john.  i say it again and again.  the reason i do: because i want you to report it eventually.\n" +
                    "go ahead, steve.\n" +
                    "q\thow would that work without widespread —\n" +
                    "the president:  go ahead, steve.\n" +
                    "q\t— testing though, mr. president?\n" +
                    "q\twhat do you want to hear from president xi tonight?  what do you want to talk to him about?\n" +
                    "the president:  yeah.  it’s his call.  i mean, i’ll talk to him whenever he wants to.\n" +
                    "q\t(inaudible.)\n" +
                    "the president:   i mean, you know, we’ll have, i think, a very fruitful call.  we’ll have a good call tonight at 9 o’clock.\n" +
                    "please.\n" +
                    "q\ti mean, is it about the virus though, sir?\n" +
                    "the president:  yes, in the back.\n" +
                    "we’ll be talking about that, yeah.  we’ll be talking about the virus.\n" +
                    "q\tthank you.\n" +
                    "q\tso, as the previous —\n" +
                    "the president:  yeah.  in the back first, please.\n" +
                    "q\tthank you, mr. president.  despite the jobless claim numbers today, the market rallied again.  it’s up over 4,000 points in the last three days (inaudible) —\n" +
                    "the president:  yeah.  record.\n" +
                    "q\tyeah.  the largest charge since 1931.  do you think that the economic uncertainty has passed, given the market?\n" +
                    "the president:  no, not yet.  it hasn’t passed, but it’s come a long way.  i think they think we’re doing a really good job, in terms of running this whole situation, having to do with the virus.  i think they feel that — i think they feel the administration — myself and the administration — are doing a good job with people — keeping, very importantly, people informed.  because there was a great fear.\n" +
                    "and a lot of good things are happening.  the mortality rate is at a, in my opinion — you’ll have to speak to deborah, tony, all of the others — but in my opinion, it’s way, way down.  and that takes a lot of fear out.  you know, it’s one thing to have it; it’s another thing to die.\n" +
                    "you know, when i first got involved, i was being told numbers that were much, much higher than the number that seems to be.  and remember that people that have it — many people have it.  i just spoke to two people.  they had it.  they never went to a doctor.  they had it — absolutely had it — but they never went to a doctor.  they never went to anything.  they didn’t even report it.  you have thousands and — hundreds of thousands of cases like that.  so you have to add that to the caseload also.\n" +
                    "and the people that actually die, that percentage is — is a much lower percentage than i ever thought.  that’s one of the reasons i say, “look, we’re going to beat this and we’re going to get back to work.”\n" +
                    "q\ti have one more question —\n" +
                    "the president:  yes, please.\n" +
                    "q\t— on more news from your administration today.  the doj announced charges against nicolás maduro for drug trafficking.  they’ve designated him as a narco-terrorist.\n" +
                    "the president:  sounds appropriate.\n" +
                    "q\tit’s also expected that venezuela is going to get hit really hard by the coronavirus.  does the administration see this as a weak point for the maduro regime?\n" +
                    "the president:  well, no, no, i — we don’t look at a weak point.  this is a serious problem for over 150 nations — the virus.  i would say this: maduro and venezuela, we’re watching it very closely.  we’ll see what happens.  but that is correct: those charges were made.\n" +
                    "please.\n" +
                    "q\tyou said a moment ago that you used the defense production act on two minor occasions?\n" +
                    "the president:  yeah.\n" +
                    "q\twhat were those occasions?\n" +
                    "the president:  we will give you that notification.  we’ll let you know.  okay?\n" +
                    "q\tthank you, sir.  can i follow up on john’s question about the classifying for counties?  a lot of these areas have not done testing yet.  is it safe to say that the current guidelines will be extended into next week?  will you — will you wait to change those guidelines until you have the —\n" +
                    "the president:  yeah.\n" +
                    "q\t— the data (inaudible)?\n" +
                    "the president:  i want those guidelines to go, even when we’re open and fully operational.  and, frankly, much of the guidelines, like shaking hands — maybe people aren’t going to be shaking hands anymore.\n" +
                    "you know, tony had mentioned to me — tony fauci — the other day that — i don’t think he was — would be too upset with the concept of not shaking hands.  he was saying that the flu would cut down — the regular flu would be cut down by quite a bit if we didn’t do that, if we didn’t shake hands.  you know, the regular flu, of which — you know, you have a lot of deaths and a lot of problems with that too.  so i think a lot of — a lot of great things are going to —\n" +
                    "when we’re open — just so — just to finish.  when we’re open, as soon as we open, that doesn’t mean you’re going to stop with the guidelines.  you’ll still try and distance yourself.  maybe not to the same extent because you have to lead a life, but i think the time is coming.\n" +
                    "how about one more question?  go ahead, in the back, please.\n" +
                    "q\tthank you very much, mr. president.\n" +
                    "the president:  go ahead, please.\n" +
                    "q\ti also have two questions, because i’m asking on behalf of foreign press as well.  so one domestic question, one international.  domestically, you just tweeted the other day, saying that it’s very important that we totally protect asian americans.\n" +
                    "the president:  yes, i do.  very important to me.\n" +
                    "q\tbut still millions —\n" +
                    "the president:  it’s very important to me.  they have to — we have to protect our asian americans.  it’s very — it’s a very important — that was a very important tweet to me because i didn’t like things that i was hearing.\n" +
                    "please, go ahead.\n" +
                    "q\twhat’s — what’s the concrete measure that you’re taking to combat the hate crimes against asian —\n" +
                    "the president:  well, i don’t know.  all i know is this: asian americans in our country are doing fantastically well.  i’m very close to them, as you know, and they’re doing fantastically well.  and i think they appreciate the job we’re doing.\n" +
                    "but i did want to put that statement out — the social media statement — because, to me, asian americans are a great part of our country.\n" +
                    "thank you all very much.  we’ll see you soon.  thank you.\n" +
                    "the vice president:  thank you, mr. president.\n" +
                    "well, good afternoon everyone.  the white house coronavirus task force met today.  we continue to move out on president trump’s directive to slow the spread with mitigation, to advance and expand testing across the country, and to work on the critical supplies that our healthcare workers and our nation needs.\n" +
                    "today, the president convened the nation’s governors — all of the states and territories — and reiterated to them that, with fema in the lead, our approach is — our response to the coronavirus in this country is locally executed — healthcare workers, local public health officials; it is state managed; and it is federally supported.\n" +
                    "we took the opportunity to thank all of the governors across the country, our states, and territories for their incredible leadership and the partnership they forged with this administration.  we were able to confirm that 10 major disaster declarations have been issued, most recently to new jersey, north carolina, florida, texas, and illinois.\n" +
                    "we spoke to them about the importance of the economic recovery legislation that will come before the house of representatives, we believe, tomorrow midday.  and we’re grateful for their efforts in working for that unanimous bipartisan vote in the united states senate last night.\n" +
                    "in addition to direct payments for american families, the average family of four will receive a direct payment of some $3,400.  they’ll also receive payroll report [sic].  many of the small businesses, even those restaurants that you just spoke about, will now be able to keep people on the payroll for a period of months, even if the restaurant or the business is not open.\n" +
                    "with the governors though, we talked about the $150 billion in direct aid to the states, $100 billion in direct assistance to hospitals, and of course, the expansion of unemployment benefits to make sure that our states have the resources to meet the challenges that were so evident in those unemployment numbers this morning.\n" +
                    "on the subject of testing, i’m pleased to report that testing is available in all 50 states.  and in partnership with commercial labs across america, this morning we received word that 552,000 tests have been performed and completed all across the united states.\n" +
                    "we want to thank the american hospital association and hospitals across the country that are just now beginning to report in a fulsome way the results of those tests.  and when the president signs the law tomorrow, it’ll actually be required by law.  but as dr. birx and dr. fauci have explained many times at this podium, it’s so important that any hospital or any lab that’s doing testing report back to the cdc and fema so we have full visibility to provide the president with the very best counsel.\n" +
                    "good news today on testing: abbott laboratories submitted to the fda today a request for approval of a point-of-care test.  this would be the kind of test where you could go to your doctor, you could get the test done there at your doctor, and have the results in no more than 15 minutes.  dr. steve hahn will be here tomorrow to talk about progress in evaluating abbott laboratories’ point-of-care tests.\n" +
                    "also, speaking of the fda, on the subject of swabs: the fda did announce earlier this week that testing symptomatic patients by swabbing from the front of the nose is perfectly appropriate, and it’s already begun across the country.  it allows for self-collection, and it also relieves the burden on healthcare protection.  it saves — it saves personal protective equipment from being expended when people can administer a test themselves.  and dr. birx will speak about the importance and the availability of swabs.\n" +
                    "with regard to supplies, you heard the president speak about what we have already shipped out from the national strategic stockpile: more than 9 million n95 masks for healthcare workers, 20 million surgical masks, 6,000 ventilators, and millions of gloves and gowns and face shields.\n" +
                    "in addition to that, we are working with a number of suppliers to manufacture ventilators, even while we work with state leaders to assess not just what ventilators are available in their state hospitals, but what ventilators are available in private hospitals across their state.  and governors across the country are doing great work evaluating the full supply of tens of thousands of ventilators that are available.\n" +
                    "let me also say thank you to the american society of anesthesiologists and dr. mary peterson.  the american society of anesthesiologists actually produced a video — tomorrow they will host a webinar — for healthcare workers to demonstrate how the devices that anesthesiologists use can be very easily converted into a ventilator that’s appropriate for a patient struggling with respiratory ailment like the coronavirus.  and we’re very grateful.  this actually adds tens of thousands of devices to the supply and we’re all truly grateful to dr. peterson and the whole association for their full cooperation.\n" +
                    "with 5 of 10 counties in the country being the top counties for coronavirus being in the new york city metropolitan area, let us reiterate our recommendation that any resident of the greater new york city area who has traveled elsewhere in the united states, please check your temperature, mind your health, and self-isolate for 14 days.\n" +
                    "what we don’t want — again, with 5 of the top 10 counties for coronavirus being in the greater new york city area, we don’t want anyone — and no one would want to inadvertently carry the coronavirus to a community or to a family member inadvertently, because they’ve come out of that community.  so again, as dr. fauci said recently, if you’ve come out of new york over the last several weeks, before the mitigation measures were put into effect in particular, check your temperature, mind your health, and self-isolate for 14 days.\n" +
                    "it is inspiring — as i prepare to introduce dr. birx to talk about the data that we’re monitoring on a regular basis and dr. fauci to talk about mitigation, let me conclude by saying how inspiring it is to see the way america and the american people are responding to this moment.\n" +
                    "we all were awakened this morning with record unemployment numbers — not unexpected during the time of national crisis that we are facing.  but what you may not know is that while there were some 3 million-plus new unemployment claims, walmart announced they’re hiring 150,000 new associates through the end of may; amazon, a few weeks back, announced they’re hiring 100,000 additional warehouse employees to meet a growing demand at amazon for online purchases; cvs health is going to provide bonuses to their employees working with patients and hire 50,000 workers; and pizza hut, i’m told, is planning to hire more than 30,000 permanent employees.  and that’s just a short summary of a list that totals almost a half a million jobs that have been announced by businesses around america.\n" +
                    "and it’s not just been about jobs; it’s been about generosity.  anheuser-busch is making a $5 million donation to the american red cross to support first responders; oyo hotels is offering doctors, nurses, and first responders free rooms at any of their 300 hotels all across america; and the pharmaceutical company abbvie is actually donating $25 million to the international medical corps and to feeding america.\n" +
                    "american businesses are stepping up to partner with us to meet this moment, but they’re being incredibly generous as well to all of those that are stepping in in the lead and providing healthcare services and, of course, to those most in need.\n" +
                    "we can do this, america, but it’ll take all of us.  as the president often says, “we’re all in this together.”  but as millions of americans do their part with “15 days to slow the spread,” putting into practice the president’s guidelines for combating the coronavirus and its spread, we grow more confident every day that this too shall pass.  with the cooperation and generosity and prayers of the american people, i just know that we will slow the spread, we will protect our most vulnerable, and we will heal our land.\n" +
                    "and now for information on the latest on data and what we’re seeing, dr. deborah birx.\n" +
                    "dr. birx:  thank you.  thank you, mr. vice president.  so just as a summary of where it looks domestically — i won’t talk so much about the global issues this time: we do have 19 out of our 50 states, to be reminded, that had early cases but have persistently low level of cases and, at this point, have less than 200 cases.  so that’s almost 40 percent of the country with extraordinarily low numbers.\n" +
                    "and they are testing.  some of our governors have been very adamant about their need for test kits.  we have gotten them test kits.\n" +
                    "when we had abbott add — about a week ago — to the test kits, we’ve been able to open up additional test kits for our states that want to do surveillance and want to do contact tracing.  these 19 states are doing still active containment.  there’re at 200 cases despite the fact that they’ve been measuring them over the last three to four weeks.\n" +
                    "still though, 55 percent all cases and 55 percent of all new cases continue out of the new york metro area; that’s the new jersey part and new york part, in particular.  i haven’t added in connecticut or other counties at this point.\n" +
                    "we are concerned about certain counties that look like they’re having a more rapid increase, if we look at wayne county in michigan and you look at cook county in chicago.  so we have integrated all of our information to not only look at where the cases are today, but how they’re moving, so we can alert fema to where we think the next potential hotspot is.\n" +
                    "all of the counties that i’ve mentioned — the hotspots are in urban areas or in the communities that serve that urban area.  and i think that’s something very important to remember as we move forward.\n" +
                    "because of the innovations within our private sector, we continue to have these new platforms added for laboratory testing.  and these become critical platforms for states that have very low rates and very low rates needed to test.  why is that important?  some of these machines have wells and plastic plates that, in order to be effective, you have to put on about almost 96 samples.  and others are made for 4 samples or 24 samples at a time.\n" +
                    "so what’s critical for us to be able to do is to match the need to the county and state.  and that’s the role that we can provide advice on because we get to see across the whole country, and where those items are needed most.  and so this is allowing us to adapt and adopt, really, allocation of tests or recommendations to state what piece of equipment they may need.\n" +
                    "the 550,000 tests: you can do the math, but we’re still running somewhere about 14 percent overall.  that means 86 percent of the people with significant symptoms — because remember, you had to have a fever and symptoms to get tested at this point — so, still 86 percent are negative.  these are really important facts for the american people.\n" +
                    "i’m sure many of you saw the recent report out of the uk about them adjusting completely their needs.  this is really quite important.  if you remember, that was the report that said there would be 500,000 deaths in the uk and 2.2 million deaths in the united states.  they’ve adjusted that number in the uk to 20,000.  so, half a million to 20,000.  we’re looking into this in great detail to understand that adjustment.\n" +
                    "i’m going to say something that’s a little bit complicated, but i’m going to try to do it in a way that we can all understand it together.  in the model, either you have to have a large group of people who are asymptomatic, who have never presented for any test, in order to have the kind of numbers that were predicted to get to 60 million people infected or 6 million people infected.  you have to have a large group of asymptomatics, because in no country to date have we seen an attack rate over 1 in 1,000.\n" +
                    "so either we’re only measuring the tip of the iceberg of the symptomatic cases and underneath it are a large group of people.  so we’re working very hard to get that antibody test because that’s a good way to figure out who are all these people under here and do they exist.  or we have the transmission completely wrong.\n" +
                    "so these are the things we’re looking at because the predictions of the models don’t match the reality on the ground in either china, south korea, or italy.\n" +
                    "we are about five times the size of italy.  so if we were italy and you did all those divisions, italy should have close to 400,000 deaths.  they’re not close to achieving that.  so these are the kinds of things we’re trying to understand.  models are models.  we’re adapting now to the — there’s enough data now of the real experience with the coronavirus on the ground to really make these predictions much more sound.\n" +
                    "so when people start talking about 20 percent of a population getting infected, it’s very scary.  but we don’t have data that matches that, based on the experience.\n" +
                    "and then, finally, the situation about ventilators.  we were reassured, in meeting with our colleagues in new york, that there are still icu beds remaining and there’s still significant — over 1,000 or 2,000 — ventilators that have not been utilized yet.\n" +
                    "please, for the reassurance of people around the world — to wake up this morning and look at people talking about creating dnr situations — do-not-resuscitate situations — for patients, there is no situation in the united states right now that warrants that kind of discussion.  you can be thinking about it in a hospital — certainly many hospitals talk about this on a daily basis — but to say that to the american people, to make the implication that when they need a hospital bed, it’s not going to be there, or when they need that ventilator, it’s not going to be there — we don’t have evidence of that right now.  and it’s our job collectively to assure the american people that — it’s our collective job to make sure that doesn’t happen.\n" +
                    "right now, you can see these state — these cases are concentrated in highly urban areas.  there are other parts of the states that have lots of ventilators and other parts of new york state that don’t have any infections right now.\n" +
                    "so we can be creative.  we can meet the need by being responsive.  but there’s no model right now — i mean, no reality on the ground where we can see that 60 to 70 percent of americans are going to get infected in the next 8 to 12 weeks.  i just want to be clear about that.\n" +
                    "so we’re adapting to the reality on the ground.  we’re looking at the models of how they can inform.  but we also are learning very clearly from south korea and from italy and from spain.\n" +
                    "just to — because i know many of you will look up my numbers — the only people who are over, really, 1 in 1,000 cases are people that have very small populations, like monaco and liechtenstein.  so you will see a different number coming from when your population is really tiny; one case can put you over 1 to 1,000 or 2 to 1,000.  thank you.\n" +
                    "the vice president:  good.  we’ll do questions in a moment.\n" +
                    "dr. fauci.\n" +
                    "dr. fauci:   thank you mr. vice president.  i’m going to change the topic just a little bit because there was questions that came up and i’ve been asked about this on a couple of media interactions regarding the interventions that we’re talking about.  and it’s important because it’s about something that i said yesterday, about what we would likely see.\n" +
                    "whenever you put the clamps down and shut things down, you do it for two reasons: you do it to prevent the further spread — as we call, mitigation — but you also do it to buy yourself time to get better prepared for what might be a rebound.  it may be a rebound that we get things really under control, and then you pull back, which ultimately we’re going to have to do.  everybody in the world is going to have to do that.  you’re either going to get a rebound, or it might cycle into the next season.\n" +
                    "so what are we going to do to prepare ourselves for that?  one of the most important things is one that i mentioned several times from this podium, and that is to clarify a bit about the timeline for vaccines and would that have any real impact on what we would call “the rebound” or what we call a “cycling in the season.”\n" +
                    "certainly, for sure, a vaccine is not going to help us now, next month, the month after.  but as i mentioned to you, we went into a phase one trial.  and i keep referring to one vaccine; there’s more than one.  there’s a couple of handfuls of vaccines at different stages of development, but they’re all following the same course.  and the course is: you first go into a phase one trial to see if it’s safe and you have very few people, 45 people, within a certain age group — all healthy, none at really any great risk of getting infected.   and the reason you do that is because you want to make sure that it’s safe.\n" +
                    "then the next thing you do — and that takes about three months, easily, maybe more.  so that’s going to bring us into the beginning or middle of the summer.\n" +
                    "then you go to a phase two trial, or what we say “two-three,” which means we’re going to put a lot of people in there.   now we hope that there aren’t a lot of people getting infected, but it is likely there will be somewhere in the world where that’s going on.  so it’s likely that we will get what’s called an “efficacy signal,” and we will know whether or not it actually works.\n" +
                    "if, in fact, it does, we hope to rush it to be able to have some impact on recycling in the next season.  and like i said, that could be a year to a year and a half.  i’m not changing any of the dates that i mentioned.\n" +
                    "but one of the things that we are going to do that you need to understand — that has been a stumbling block for previous development of vaccines — and that is, even before you know something works, at risk, you have to start producing it.  because once you know it works, you can’t say, “great, it works.  now give me another six months to produce it.”  so we’re working with a variety of companies to take that risk.  we didn’t take it with zika.  that’s why, you know, we have a nice zika vaccine but we don’t have enough to do it because there’s no zika around.  same with sars.  so that’s one of the things we’re really going to push on is to be able to have it ready, if in fact, it works.\n" +
                    "now, the issue of safety — something that i want to make sure the american public understand: it’s not only safety when you inject somebody and they get maybe an idiosyncratic reaction, they get a little allergic reaction, they get pain.  there’s safety associated — “does the vaccine make you worse?”  and there are diseases in which you vaccinate someone, they get infected with what you’re trying to protect them with, and you actually enhance the infection.  you can get a good feel for that in animal models.  so that’s going to be interspersed at the same time that we’re testing.  we’re going to try and make sure we don’t have enhancement.\n" +
                    "so the worst possible thing you do is vaccinate somebody to prevent infection and actually make them worse.\n" +
                    "next, and finally, with regard — i’ll get you — to your question.  finally, with regard to therapies — i mean, we keep getting asked about therapies.  there’s a whole menu of therapies that are going into clinical trial.  as i’ve told you all and i’ll repeat it again: the best way to get the best drug as quickly as possible is to do a randomized controlled trial so that you know is it safe and is it effective.  if it’s not effective, get it off the board and go to the next thing.  if it is effective, get it out to the people that need it.\n" +
                    "so you’re going to be hearing, over the next month or more, about different drugs that are going to go into these randomized controlled trials.  and i feel confident, knowing about what this virus is and what we can do with it, that we will have some sort of therapy that’ll give at least a partial, if not a very good protection in preventing progression of disease.  and we’ll be back here talking about that a lot, i’m sure.\n" +
                    "thank you.\n" +
                    "the vice president:  tony, if you want, you can take a question.  go ahead.\n" +
                    "q\tif i could just get back to what you’re saying about this idea of risk in drug manufacture.  are you saying that at some point in the phase two trials, that if you’re seeing some form of efficacy, that you may try to convince a laboratory to spool up production at that point so that there is a reservoir on hand?\n" +
                    "dr. fauci:  even before.\n" +
                    "q\teven before.\n" +
                    "dr. fauci:  when i go into phase two, i want to find somebody that is going to make it.\n" +
                    "q\tand who would pay for that?\n" +
                    "dr. fauci:  well, partially the federal government, i think, in some respects, to de-risk it, but also investments by the companies.  a lot of companies are not shy now about doing that.  usually, when you do that at risk, john, you’ve got to give some backup for them, and we’ve done that.  we’ve put hundreds of millions of dollars into companies to try and make vaccines.  i wouldn’t hesitate to do that for a moment now.\n" +
                    "q\ton the county issue, i was talking about having low-risk, medium-risk, and high-risk counties — or for dr. birx — but there is no domestic travel restrictions.  what’s to prevent somebody from a high-risk county going to a low-risk county?  don’t you risk creating a patchwork system, allowing more cases to slip through the cracks and the virus to spread in other areas of the country?\n" +
                    "dr. birx:  i think this is a very important concept and it’s why we’ve really worked on messaging to the american people about these “15 days to stop the spread,” because part of this will be the need to have highly responsible behavior between counties.  and i think the american people can understand that — that they will understand where the virus is because we’ll have the testing data and where it isn’t, and make sure that they’re taking appropriate precautions as they move in and out of spaces.\n" +
                    "i think this will be critical for our future as we work together to really understand where the virus is and where it isn’t in real time.\n" +
                    "q\tdr. birx, if i could just follow up on, sort of, your modeling.  everyone is talking about this neil ferguson study out of imperial and how the modeling has changed.  you, last week, said — or it was on monday that you talked about a serology test, something promising coming out of singapore.  where are we on a serology test?  the president said “very quickly.”  and then is that what you need to do some sort of community survey, so you can get to some of these xs and zs so you can figure this all out?\n" +
                    "dr. birx:  so we’re talking to cdc right now.  they are extraordinary in outbreaks and contact tracing.  so they are going to be the workforce behind any new strategy that looks at counties that need to completely move into containment and surveillance and contact tracing.\n" +
                    "but part of what they’re looking at now is where are these antibody assays.  to be clear, there is antibody assays available right now, but they’re by elisa.  and what we’re trying to do is not just do elisas because they can use that now, but to be able to have point-of-care rapid diagnostics like we have with hiv — where you just get a drop of blood, you put it on a little cassette, and it tells you if you’re positive or negative.  so that’s what companies are working on.\n" +
                    "q\tthat’s the igg?  the —\n" +
                    "dr. birx:  yes, correct.  that’s the igg to measure.  now, remember, that’s not going to be helpful in diagnosis.\n" +
                    "q\tbut that will get you —\n" +
                    "dr. birx:  that’s going to be helpful for us to know how many asymptomatic cases there are — or were.\n" +
                    "q\thow close are we to figuring out what the asymptomatic rates are?  because that seems to be the big question here on where we are on the iceberg.\n" +
                    "dr. birx:  it is a big question.  it is a very big question.  and so we have people — the fda is working on that around the clock.  they do have applications that are coming in.  we put out a call for applications.  i’ve been talking about it from this podium.  if you have an igg assay rapid test, not an elisa — i mean, you could do the elisa today because the sars — the original sars antigens react very well to the current covid-19 antibodies that people have.  but we’re really working on that, both so that we could have therapeutics that could be plasma-derived.\n" +
                    "so, thank you.  yes we’re very much focused on that.\n" +
                    "q\tdr. birx, how soon will you be able to — how soon will you be able to classify these counties?  because there’s a lot of testing that has not been done there.\n";
        }

        @Test(timeout = TIMEOUT)
        public void testCNNBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{22, 580, 830, 1058, 1241, 4759, 6472, 8187, 12730, 13908, 14091, 14535, 14892, 15082, 15254, 18951, 23537, 24210, 24496, 24920, 24959, 26129, 26212, 29705, 31032, 37941, 40101, 44448, 46952, 48848, 49122, 51270, 52109, 52975}));
            List<Integer> actual = PatternMatching.bruteForce("coronavirus", CNNtext, comparator);

            assertEquals(expected, actual);
            assertEquals(55234, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testCNNBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{22, 580, 830, 1058, 1241, 4759, 6472, 8187, 12730, 13908, 14091, 14535, 14892, 15082, 15254, 18951, 23537, 24210, 24496, 24920, 24959, 26129, 26212, 29705, 31032, 37941, 40101, 44448, 46952, 48848, 49122, 51270, 52109, 52975}));
            List<Integer> actual = PatternMatching.boyerMoore("coronavirus", CNNtext, comparator);

            assertEquals(expected, actual);
            assertEquals(6845, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testCNNKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{22, 580, 830, 1058, 1241, 4759, 6472, 8187, 12730, 13908, 14091, 14535, 14892, 15082, 15254, 18951, 23537, 24210, 24496, 24920, 24959, 26129, 26212, 29705, 31032, 37941, 40101, 44448, 46952, 48848, 49122, 51270, 52109, 52975}));
            List<Integer> actual = PatternMatching.kmp("coronavirus", CNNtext, comparator);

            assertEquals(expected, actual);
            assertEquals(54617, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{6961, 29004, 35811, 39017, 39461, 43741, 44006, 44342, 44467, 46800, 51747}));
            List<Integer> actual = PatternMatching.bruteForce("coronavirus", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(65069, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{6961, 29004, 35811, 39017, 39461, 43741, 44006, 44342, 44467, 46800, 51747}));
            List<Integer> actual = PatternMatching.boyerMoore("coronavirus", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(7738, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{6961, 29004, 35811, 39017, 39461, 43741, 44006, 44342, 44467, 46800, 51747}));
            List<Integer> actual = PatternMatching.kmp("coronavirus", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(64726, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpGreatBrute() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{515, 1236, 1421, 1629, 2399, 3092, 3702, 3912, 4098, 4721, 5642, 5666, 8015, 8196, 9969, 9989, 13646, 14387, 14863, 16850, 16974, 18533, 19583, 19736, 22331, 22484, 22715, 23680, 25487, 26208, 26382, 31559, 31583, 34472, 37380, 38837, 43240, 44125, 44367, 50367, 55788, 56944}));
            List<Integer> actual = PatternMatching.bruteForce("great", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(64765, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpGreatBM() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{515, 1236, 1421, 1629, 2399, 3092, 3702, 3912, 4098, 4721, 5642, 5666, 8015, 8196, 9969, 9989, 13646, 14387, 14863, 16850, 16974, 18533, 19583, 19736, 22331, 22484, 22715, 23680, 25487, 26208, 26382, 31559, 31583, 34472, 37380, 38837, 43240, 44125, 44367, 50367, 55788, 56944}));
            List<Integer> actual = PatternMatching.boyerMoore("great", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(16539, comparator.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testTrumpGreatKMP() {
            List<Integer> expected = new ArrayList<>(Arrays.asList(new Integer[]{515, 1236, 1421, 1629, 2399, 3092, 3702, 3912, 4098, 4721, 5642, 5666, 8015, 8196, 9969, 9989, 13646, 14387, 14863, 16850, 16974, 18533, 19583, 19736, 22331, 22484, 22715, 23680, 25487, 26208, 26382, 31559, 31583, 34472, 37380, 38837, 43240, 44125, 44367, 50367, 55788, 56944}));
            List<Integer> actual = PatternMatching.kmp("great", TrumpText, comparator);

            assertEquals(expected, actual);
            assertEquals(64569, comparator.getComparisonCount());
        }
    }
}
