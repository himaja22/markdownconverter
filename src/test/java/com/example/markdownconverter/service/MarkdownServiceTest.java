package com.example.markdownconverter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MarkdownServiceTest {

	// Instance of the service to be tested
	private final MarkdownService markdownService = new MarkdownService();

	/**
	 * Test case for converting headings in Markdown to HTML.
	 * Markdown headings are expected to be converted to corresponding HTML header tags.
	 */
	@Test
	public void testHeadingConversion() {
		String markdown = "# Heading 1\n\n## Heading 2\n\n### Heading 3";
		String expectedHtml = "<h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for converting a link in Markdown to HTML.
	 * Markdown links should be converted to HTML anchor tags with correct href attribute.
	 */
	@Test
	public void testLinkConversion() {
		String markdown = "This is a [link](http://example.com)";
		String expectedHtml = "<p>This is a <a href=\"http://example.com\">link</a></p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling multiple paragraphs in Markdown.
	 * Each paragraph in Markdown should be converted to a separate HTML paragraph tag.
	 */
	@Test
	public void testParagraphHandling() {
		String markdown = "This is a paragraph.\n\nThis is another paragraph.";
		String expectedHtml = "<p>This is a paragraph.</p><p>This is another paragraph.</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling empty lines within text in Markdown.
	 * Empty lines should create new paragraphs in the HTML output.
	 */
	@Test
	public void testEmptyLinesInText() {
		String markdown = "First line\n\nSecond line";
		String expectedHtml = "<p>First line</p><p>Second line</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for converting headings followed by paragraphs in Markdown.
	 * Headings and paragraphs should be correctly converted to HTML with appropriate tags.
	 */
	@Test
	public void testHeadingWithParagraphs() {
		String markdown = "# Heading 1\n\nThis is some text.\n\n## Heading 2\n\nMore text.";
		String expectedHtml = "<h1>Heading 1</h1><p>This is some text.</p><h2>Heading 2</h2><p>More text.</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for mixed content in Markdown, including headings, paragraphs, and links.
	 * The Markdown should be converted to HTML with correct handling of mixed content.
	 */
	@Test
	public void testMixedContent() {
		String markdown = "# Heading\n\nThis is a [link](http://example.com) inside a paragraph.";
		String expectedHtml = "<h1>Heading</h1><p>This is a <a href=\"http://example.com\">link</a> inside a paragraph.</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling multiple headings in Markdown.
	 * Multiple headings should be converted to corresponding HTML header tags.
	 */
	@Test
	public void testMultipleHeadings() {
		String markdown = "# Heading 1\n\n## Heading 2\n\n### Heading 3\n\n# Heading 4";
		String expectedHtml = "<h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3><h1>Heading 4</h1>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling an empty Markdown input.
	 * An empty Markdown string should result in an empty HTML output.
	 */
	@Test
	public void testNoContent() {
		String markdown = "";
		String expectedHtml = "";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling trailing newlines in Markdown.
	 * Trailing newlines should not affect the final HTML output.
	 */
	@Test
	public void testTrailingNewlines() {
		String markdown = "# Heading\n\nThis is a paragraph.\n\n";
		String expectedHtml = "<h1>Heading</h1><p>This is a paragraph.</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling malformed Markdown input.
	 * Malformed links or incomplete syntax should be handled gracefully, with Markdown syntax left as plain text.
	 */
	@Test
	public void testMalformedMarkdown() {
		String markdown = "# Heading\n[Link without URL]";
		String expectedHtml = "<h1>Heading</h1><p>[Link without URL]</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	/**
	 * Test case for handling invalid Markdown syntax.
	 * Invalid or incomplete Markdown syntax should be preserved as plain text in the HTML output.
	 */
	@Test
	public void testInvalidMarkdownSyntax() {
		String markdown = "# Heading\n\n**Bold text\n\n";
		String expectedHtml = "<h1>Heading</h1><p>**Bold text</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}


	@Test
	public void testSampleDocument() {
		String markdown = "# Sample Document\n\nHello!\n\nThis is sample markdown for the [Mailchimp](https://www.mailchimp.com) homework assignment.";
		String expectedHtml = "<h1>Sample Document</h1><p>Hello!</p><p>This is sample markdown for the <a href=\"https://www.mailchimp.com\">Mailchimp</a> homework assignment.</p>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}

	@Test
	public void testHeaderWithMultipleParagraphsAndLinks() {
		String markdown = "# Header one\n\nHello there\n\nHow are you?\nWhat's going on?\n\n## Another Header\n\nThis is a paragraph [with an inline link](http://google.com). Neat, eh?\n\n## This is a header [with a link](http://yahoo.com)";
		String expectedHtml = "<h1>Header one</h1><p>Hello there</p><p>How are you? What's going on?</p><h2>Another Header</h2><p>This is a paragraph <a href=\"http://google.com\">with an inline link</a>. Neat, eh?</p><h2>This is a header [with a link](http://yahoo.com)</h2>";

		String actualHtml = markdownService.convertMarkdownToHtml(markdown);
		assertEquals(expectedHtml, actualHtml);
	}



}
