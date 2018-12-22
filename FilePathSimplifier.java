package apps;

import java.util.Deque;
import java.util.LinkedList;

public class FilePathSimplifier {
	private static final String ROOT = "/";
	private static final String CURRENT_DIRECTORY = ".";
	private static final String PREVIOUS_DIRECTORY = "..";
	Deque<String> deque = new LinkedList<>();

	public String simplifyPath(String filePath) throws FilePathException {
		String[] parts = filePath.split(ROOT);
		deque.push(ROOT);
		for (String dir : parts) {
			if (dir.equals(CURRENT_DIRECTORY) || dir.isEmpty()) {
				continue;
			} else if (dir.equals(PREVIOUS_DIRECTORY)) {
				if (deque.peek().equals(ROOT)) {
					throw new FilePathException("Path cannot go beyond root");
				} else {
					deque.pop();
				}
			} else {
				deque.push(dir);
			}
		}

		StringBuilder builder = new StringBuilder();
		while (!deque.isEmpty()) {
			String dir = deque.pollLast();
			builder.append(dir);
			if(!dir.equals(ROOT)) {
				builder.append(ROOT);
			}
		}
		return builder.toString();
	}

	static class FilePathException extends Exception {
		private String message;

		public FilePathException(String message) {
			super(message);
		}

	}

	public static void main(String[] args) throws FilePathException {
		String url = "/home//../sh1/a1/f1/../.././b1";
		FilePathSimplifier simplifier = new FilePathSimplifier();
		System.out.println(simplifier.simplifyPath(url));
	}
}
