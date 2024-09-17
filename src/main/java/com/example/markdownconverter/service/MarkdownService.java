package com.example.markdownconverter.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for converting Markdown text to HTML with custom logic.
 */
@Service
public class MarkdownService {

    private static final Logger logger = LoggerFactory.getLogger(MarkdownService.class);

    // Pattern to match Markdown headings (e.g., # Heading, ## Subheading)
    private static final Pattern HEADING_PATTERN = Pattern.compile("^(#{1,6})\\s+(.*)");

    // Pattern to match Markdown links (e.g., [text](url))
    private static final Pattern LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\(([^)]+)\\)");

    // Pattern to match Markdown lists (both unordered (e.g., * item) and ordered (e.g., 1. item))
    private static final Pattern LIST_PATTERN = Pattern.compile("^(\\*|\\d+\\.)\\s+(.*)");

    /**
     * Converts the given Markdown text to HTML.
     */
    public String convertMarkdownToHtml(String markdownText) {
        // Log the start of the conversion process
        logger.info("Starting Markdown to HTML conversion");

        // StringBuilder to accumulate the resulting HTML
        StringBuilder htmlOutput = new StringBuilder();
        // Split the input Markdown text into lines
        String[] lines = markdownText.split("\\r?\\n");

        boolean inParagraph = false;
        StringBuilder paragraphLines = new StringBuilder();

        try {
            for (String line : lines) {
                // Trim whitespace from the line
                line = line.trim();

                // Log each line being processed
                logger.debug("Processing line: {}", line);

                // Handle empty lines to close paragraphs
                if (line.isEmpty()) {
                    if (inParagraph) {
                        // Log paragraph closure
                        logger.debug("Ending paragraph");
                        // Append the accumulated paragraph content to HTML
                        appendParagraph(htmlOutput, paragraphLines);
                        inParagraph = false;
                    }
                    continue;
                }

                // Process headings
                Matcher headingMatcher = HEADING_PATTERN.matcher(line);
                if (headingMatcher.matches()) {
                    // Log heading detection
                    logger.debug("Detected heading: {}", line);
                    // Extract heading level and content
                    int level = headingMatcher.group(1).length();
                    String content = headingMatcher.group(2);
                    if (inParagraph) {
                        // Close the previous paragraph
                        logger.debug("Ending paragraph before heading");
                        appendParagraph(htmlOutput, paragraphLines);
                        inParagraph = false;
                    }
                    // Append the heading to HTML
                    htmlOutput.append(String.format("<h%d>%s</h%d>", level, content, level));
                    continue;
                }

                // Process lists
                Matcher listMatcher = LIST_PATTERN.matcher(line);
                if (listMatcher.matches()) {
                    // Log list item detection
                    logger.debug("Detected list item: {}", line);
                    if (inParagraph) {
                        // Close the previous paragraph
                        logger.debug("Ending paragraph before list");
                        appendParagraph(htmlOutput, paragraphLines);
                        inParagraph = false;
                    }
                    // Extract list item content
                    String listItem = listMatcher.group(2);
                    if (line.startsWith("*")) {
                        // Append an unordered list item
                        htmlOutput.append(String.format("<ul><li>%s</li></ul>", listItem));
                    } else {
                        // Append an ordered list item
                        htmlOutput.append(String.format("<ol><li>%s</li></ol>", listItem));
                    }
                    continue;
                }

                // Process links
                line = LINK_PATTERN.matcher(line).replaceAll("<a href=\"$2\">$1</a>");
                // Log link conversion
                logger.debug("Converted line with links: {}", line);

                // Append the line to the current paragraph
                if (!inParagraph) {
                    inParagraph = true;
                }
                paragraphLines.append(line).append(" ");
            }

            // Append the last paragraph if it exists
            if (inParagraph && paragraphLines.length() > 0) {
                logger.debug("Ending final paragraph");
                appendParagraph(htmlOutput, paragraphLines);
            }

            // Log successful completion of the conversion process
            logger.info("Markdown to HTML conversion completed successfully");

        } catch (Exception e) {
            // Log any errors that occur during conversion
            logger.error("Error converting Markdown to HTML", e);
            // Return a generic error message in HTML
            return "<p>An error occurred while processing the Markdown text.</p>";
        }

        // Return the final HTML output
        return htmlOutput.toString();
    }

    /**
     * Appends the accumulated paragraph content to the HTML output.
     */
    private void appendParagraph(StringBuilder htmlOutput, StringBuilder paragraphLines) {
        if (paragraphLines.length() > 0) {
            // Wrap the paragraph content in <p> tags
            htmlOutput.append("<p>").append(paragraphLines.toString().trim()).append("</p>");
            // Clear the paragraphLines StringBuilder for the next paragraph
            paragraphLines.setLength(0);
        }
    }
}
