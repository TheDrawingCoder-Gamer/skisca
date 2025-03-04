package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

/**
 * <p>A class that locates boundaries in text.  This class defines a protocol for
 * objects that break up a piece of natural-language text according to a set
 * of criteria.  Instances or subclasses of BreakIterator can be provided, for
 * example, to break a piece of text into words, sentences, or logical characters
 * according to the conventions of some language or group of languages.
 *
 * We provide four built-in types of BreakIterator:
 * <ul>
 * <li>makeSentenceInstance() returns a BreakIterator that locates boundaries
 * between sentences.  This is useful for triple-click selection, for example.
 * <li>makeWordInstance() returns a BreakIterator that locates boundaries between
 * words.  This is useful for double-click selection or "find whole words" searches.
 * This type of BreakIterator makes sure there is a boundary position at the
 * beginning and end of each legal word.  (Numbers count as words, too.)  Whitespace
 * and punctuation are kept separate from real words.
 * <li>makeLineInstance() returns a BreakIterator that locates positions where it is
 * legal for a text editor to wrap lines.  This is similar to word breaking, but
 * not the same: punctuation and whitespace are generally kept with words (you don't
 * want a line to start with whitespace, for example), and some special characters
 * can force a position to be considered a line-break position or prevent a position
 * from being a line-break position.
 * <li>makeCharacterInstance() returns a BreakIterator that locates boundaries between
 * logical characters.  Because of the structure of the Unicode encoding, a logical
 * character may be stored internally as more than one Unicode code point.  (A with an
 * umlaut may be stored as an a followed by a separate combining umlaut character,
 * for example, but the user still thinks of it as one character.)  This iterator allows
 * various processes (especially text editors) to treat as characters the units of text
 * that a user would think of as characters, rather than the units of text that the
 * computer sees as "characters".</ul>
 * The text boundary positions are found according to the rules
 * described in Unicode Standard Annex #29, Text Boundaries, and
 * Unicode Standard Annex #14, Line Breaking Properties.  These
 * are available at http://www.unicode.org/reports/tr14/ and
 * http://www.unicode.org/reports/tr29/.
 * <p>
 * BreakIterator's interface follows an "iterator" model (hence the name), meaning it
 * has a concept of a "current position" and methods like first(), last(), next(),
 * and previous() that update the current position.  All BreakIterators uphold the
 * following invariants:
 * <ul><li>The beginning and end of the text are always treated as boundary positions.
 * <li>The current position of the iterator is always a boundary position (random-
 * access methods move the iterator to the nearest boundary position before or
 * after the specified position, not _to_ the specified position).
 * <li>DONE is used as a flag to indicate when iteration has stopped.  DONE is only
 * returned when the current position is the end of the text and the user calls next(),
 * or when the current position is the beginning of the text and the user calls
 * previous().
 * <li>Break positions are numbered by the positions of the characters that follow
 * them.  Thus, under normal circumstances, the position before the first character
 * is 0, the position after the first character is 1, and the position after the
 * last character is 1 plus the length of the string.
 * <li>The client can change the position of an iterator, or the text it analyzes,
 * at will, but cannot change the behavior.  If the user wants different behavior, he
 * must instantiate a new iterator.</ul>
 *
 * BreakIterator accesses the text it analyzes through a CharacterIterator, which makes
 * it possible to use BreakIterator to analyze text in any text-storage vehicle that
 * provides a CharacterIterator interface.
 *
 * <b>Note:</b>  Some types of BreakIterator can take a long time to create, and
 * instances of BreakIterator are not currently cached by the system.  For
 * optimal performance, keep instances of BreakIterator around as long as makes
 * sense.  For example, when word-wrapping a document, don't create and destroy a
 * new BreakIterator for each line.  Create one break iterator for the whole document
 * (or whatever stretch of text you're wrapping) and use it to do the whole job of
 * wrapping the text.
 *
 * <P>
 * <strong>Examples</strong>:<P>
 * Creating and using text boundaries
 * <blockquote>
 * <pre>
 * public static void main(String args[]) {
 * if (args.length == 1) {
 * String stringToExamine = args[0];
 * //print each word in order
 * BreakIterator boundary = BreakIterator.makeWordInstance();
 * boundary.setText(stringToExamine);
 * printEachForward(boundary, stringToExamine);
 * //print each sentence in reverse order
 * boundary = BreakIterator.makeSentenceInstance(Locale.US);
 * boundary.setText(stringToExamine);
 * printEachBackward(boundary, stringToExamine);
 * printFirst(boundary, stringToExamine);
 * printLast(boundary, stringToExamine);
 * }
 * }
 * </pre>
 * </blockquote>
 *
 * Print each element in order
 * <blockquote>
 * <pre>
 * public static void printEachForward(BreakIterator boundary, String source) {
 * int start = boundary.first();
 * for (int end = boundary.next();
 * end != BreakIterator.DONE;
 * start = end, end = boundary.next()) {
 * System.out.println(source.substring(start,end));
 * }
 * }
 * </pre>
 * </blockquote>
 *
 * Print each element in reverse order
 * <blockquote>
 * <pre>
 * public static void printEachBackward(BreakIterator boundary, String source) {
 * int end = boundary.last();
 * for (int start = boundary.previous();
 * start != BreakIterator.DONE;
 * end = start, start = boundary.previous()) {
 * System.out.println(source.substring(start,end));
 * }
 * }
 * </pre>
 * </blockquote>
 *
 * Print first element
 * <blockquote>
 * <pre>
 * public static void printFirst(BreakIterator boundary, String source) {
 * int start = boundary.first();
 * int end = boundary.next();
 * System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Print last element
 * <blockquote>
 * <pre>
 * public static void printLast(BreakIterator boundary, String source) {
 * int end = boundary.last();
 * int start = boundary.previous();
 * System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Print the element at a specified position
 * <blockquote>
 * <pre>
 * public static void printAt(BreakIterator boundary, int pos, String source) {
 * int end = boundary.following(pos);
 * int start = boundary.previous();
 * System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Find the next word
 * <blockquote>
 * <pre>
 * public static int nextWordStartAfter(int pos, String text) {
 * BreakIterator wb = BreakIterator.makeWordInstance();
 * wb.setText(text);
 * int wordStart = wb.following(pos);
 * for (;;) {
 * int wordLimit = wb.next();
 * if (wordLimit == BreakIterator.DONE) {
 * return BreakIterator.DONE;
 * }
 * int wordStatus = wb.getRuleStatus();
 * if (wordStatus != BreakIterator.WORD_NONE) {
 * return wordStart;
 * }
 * wordStart = wordLimit;
 * }
 * }
 * </pre>
 * The iterator returned by {@link # makeWordInstance ( )} is unique in that
 * the break positions it returns don't represent both the start and end of the
 * thing being iterated over.  That is, a sentence-break iterator returns breaks
 * that each represent the end of one sentence and the beginning of the next.
 * With the word-break iterator, the characters between two boundaries might be a
 * word, or they might be the punctuation or whitespace between two words.  The
 * above code uses {@link # getRuleStatus ( )} to identify and ignore boundaries associated
 * with punctuation or other non-word characters.
 * </blockquote>
 */
object BreakIterator {
  /**
   * DONE is returned by previous() and next() after all valid
   * boundaries have been returned.
   */
    val DONE: Int = -1
  /**
   * Tag value for "words" that do not fit into any of other categories.
   * Includes spaces and most punctuation.
   */
  val WORD_NONE = 0
  /**
   * Upper bound for tags for uncategorized words.
   */
  val WORD_NONE_LIMIT = 100
  /**
   * Tag value for words that appear to be numbers, lower limit.
   */
  val WORD_NUMBER = 100
  /**
   * Tag value for words that appear to be numbers, upper limit.
   */
  val WORD_NUMBER_LIMIT = 200
  /**
   * Tag value for words that contain letters, excluding
   * hiragana, katakana or ideographic characters, lower limit.
   */
  val WORD_LETTER = 200
  /**
   * Tag value for words containing letters, upper limit
   */
  val WORD_LETTER_LIMIT = 300
  /**
   * Tag value for words containing kana characters, lower limit
   */
  val WORD_KANA = 300
  /**
   * Tag value for words containing kana characters, upper limit
   */
  val WORD_KANA_LIMIT = 400
  /**
   * Tag value for words containing ideographic characters, lower limit
   */
  val WORD_IDEO = 400
  /**
   * Tag value for words containing ideographic characters, upper limit
   */
  val WORD_IDEO_LIMIT = 500

  /**
   * Returns a new BreakIterator instance for character breaks for the default locale.
   */
  def makeCharacterInstance: BreakIterator = {
    makeCharacterInstance(null)
  }

  /**
   * Returns a new BreakIterator instance for character breaks for the given locale.
   */
  def makeCharacterInstance(locale: String): BreakIterator = {
    Stats.onNativeCall()
    new BreakIterator(_nMake(0, locale)) // UBRK_CHARACTER
  }

  /**
   * Returns a new BreakIterator instance for word breaks for the default locale.
   */
  def makeWordInstance: BreakIterator = {
    makeWordInstance(null)
  }

  /**
   * Returns a new BreakIterator instance for word breaks for the given locale.
   */
  def makeWordInstance(locale: String): BreakIterator = {
    Stats.onNativeCall()
    new BreakIterator(_nMake(1, locale)) // UBRK_WORD
  }

  /**
   * Returns a new BreakIterator instance for line breaks for the default locale.
   */
  def makeLineInstance: BreakIterator = {
    makeLineInstance(null)
  }

  /**
   * Returns a new BreakIterator instance for line breaks for the given locale.
   */
  def makeLineInstance(locale: String): BreakIterator = {
    Stats.onNativeCall()
    new BreakIterator(_nMake(2, locale)) // UBRK_LINE
  }

  /**
   * Returns a new BreakIterator instance for sentence breaks for the default locale.
   */
  def makeSentenceInstance: BreakIterator = {
    makeSentenceInstance(null)
  }

  /**
   * Returns a new BreakIterator instance for sentence breaks for the given locale.
   */
  def makeSentenceInstance(locale: String): BreakIterator = {
    Stats.onNativeCall()
    new BreakIterator(_nMake(3, locale)) // UBRK_SENTENCE
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(`type`: Int, locale: String): Long

  @ApiStatus.Internal
  @native def _nClone(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nCurrent(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nNext(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nPrevious(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nFirst(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nLast(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nPreceding(ptr: Long, offset: Int): Int

  @ApiStatus.Internal
  @native def _nFollowing(ptr: Long, offset: Int): Int

  @ApiStatus.Internal
  @native def _nIsBoundary(ptr: Long, offset: Int): Boolean

  @ApiStatus.Internal
  @native def _nGetRuleStatus(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetRuleStatuses(ptr: Long): Array[Int]

  @ApiStatus.Internal
  @native def _nSetText(ptr: Long, textPtr: Long): Unit

  try Library.staticLoad()
}

class BreakIterator @ApiStatus.Internal(ptr: Long) extends Managed(ptr, BreakIterator._FinalizerHolder
                                                                                     .PTR) with Cloneable {
  @ApiStatus.Internal var _text: U16String = null

  override def close(): Unit = {
    super.close()
    if (_text != null) _text.close()
  }

  /**
   * Create a copy of this iterator
   */
  override def clone: BreakIterator = {
    Stats.onNativeCall()
    new BreakIterator(BreakIterator._nClone(_ptr))
  }

  /**
   * Returns character index of the text boundary that was most recently
   * returned by {@link next( )}, {@link next( int )}, {@link previous( )},
   * {@link first( )}, {@link last( )}, {@link following( int )} or
   * {@link preceding( int )}. If any of these methods returns
   * {@link BreakIterator# DONE} because either first or last text boundary
   * has been reached, it returns the first or last text boundary depending
   * on which one is reached.
   */
  def current: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nCurrent(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the boundary following the current boundary. If the current
   * boundary is the last text boundary, it returns {@link BreakIterator# DONE}
   * and the iterator's current position is unchanged. Otherwise, the
   * iterator's current position is set to the boundary following the current
   * boundary.
   */
  def next: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nNext(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Advances the iterator either forward or backward the specified number of steps.
   * Negative values move backward, and positive values move forward.  This is
   * equivalent to repeatedly calling next() or previous().
   *
   * @param n The number of steps to move.  The sign indicates the direction
   *          (negative is backwards, and positive is forwards).
   * @return The character offset of the boundary position n boundaries away from
   *         the current one.
   */
  def next(n: Int): Int = {
    var m = n
    var result = 0
    if (m > 0) {
      while (m > 0 && result != BreakIterator.DONE) {
        result = next
        m = m - 1
      }
    } else if (m < 0) {
      while (m < 0 && result != BreakIterator.DONE) {
        result = previous
        m = m + 1
      }
    } else {
      result = current
    }
    result
  }

  /**
   * Returns the boundary following the current boundary. If the current
   * boundary is the last text boundary, it returns {@link BreakIterator# DONE}
   * and the iterator's current position is unchanged. Otherwise, the
   * iterator's current position is set to the boundary following the current
   * boundary.
   */
  def previous: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nPrevious(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the first boundary. The iterator's current position is set to the first text boundary.
   */
  def first: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nFirst(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the last boundary. The iterator's current position is set to the last text boundary.
   */
  def last: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nLast(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the last boundary preceding the specified character offset.
   * If the specified offset is equal to the first text boundary, it returns
   * {@link BreakIterator# DONE} and the iterator's current position is
   * unchanged. Otherwise, the iterator's current position is set to the
   * returned boundary. The value returned is always less than the offset or
   * the value {@link BreakIterator# DONE}.
   */
  def preceding(offset: Int): Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nPreceding(_ptr, offset)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the first boundary following the specified character offset.
   * If the specified offset is equal to the last text boundary, it returns
   * {@link BreakIterator# DONE} and the iterator's current position is
   * unchanged. Otherwise, the iterator's current position is set to the
   * returned boundary. The value returned is always greater than the offset or
   * the value {@link BreakIterator# DONE}.
   */
  def following(offset: Int): Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nFollowing(_ptr, offset)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if the specified character offset is a text boundary.
   */
  def isBoundary(offset: Int): Boolean = {
    try {
      Stats.onNativeCall()
      BreakIterator._nIsBoundary(_ptr, offset)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>For rule-based BreakIterators, return the status tag from the
   * break rule that determined the boundary at the current iteration position.</p>
   *
   * <p>For break iterator types that do not support a rule status,
   * a default value of 0 is returned.</p>
   *
   * @return The status from the break rule that determined the boundary
   *         at the current iteration position.
   */
  def getRuleStatus: Int = {
    try {
      Stats.onNativeCall()
      BreakIterator._nGetRuleStatus(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * For RuleBasedBreakIterators, get the status (tag) values from the break rule(s)
   * that determined the the boundary at the current iteration position.
   * <p>
   * For break iterator types that do not support rule status,
   * no values are returned.
   *
   * @return an array with the status values.
   */
  def getRuleStatuses: Array[Int] = {
    try {
      Stats.onNativeCall()
      BreakIterator._nGetRuleStatuses(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Set a new text string to be scanned. The current scan position is reset to {@link first( )}.
   */
  def setText(text: String): Unit = {
    try {
      Stats.onNativeCall()
      _text = U16String(text)
      BreakIterator._nSetText(_ptr, Native.getPtr(_text))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(_text)
    }
  }
}