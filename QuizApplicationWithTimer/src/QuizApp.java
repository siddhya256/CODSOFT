import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

class Quiz {
    private Question[] questions;
    private int score = 0;
    private int currentQuestionIndex = 0;
    private Timer timer;
    private boolean timeUp = false;

    public Quiz(Question[] questions) {
        this.questions = questions;
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            Question currentQuestion = questions[currentQuestionIndex];
            System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestionText());
            
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            startTimer(10);  // 10 seconds for each question

            int userAnswer = 0;
            while (!timeUp) {
                System.out.print("Enter your choice (1-" + options.length + "): ");
                if (scanner.hasNextInt()) {
                    userAnswer = scanner.nextInt();
                    if (userAnswer >= 1 && userAnswer <= options.length) {
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    scanner.next();  // Clear invalid input
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            if (!timeUp) {
                checkAnswer(userAnswer - 1, currentQuestion.getCorrectAnswerIndex());
                timer.cancel();
            } else {
                System.out.println("Time is up!");
            }

            timeUp = false;  // Reset for the next question
        }

        scanner.close();
        displayResults();
    }

    private void startTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
            }
        }, seconds * 1000);
    }

    private void checkAnswer(int userAnswerIndex, int correctAnswerIndex) {
        if (userAnswerIndex == correctAnswerIndex) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " + (correctAnswerIndex + 1));
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score is: " + score + " out of " + questions.length);
        System.out.println("Thank you for participating!");
    }
}
public class QuizApp {
    public static void main(String[] args) {
        Question[] questions = new Question[] {
            new Question("What is the capital of India?", new String[] {"Berlin", "Madrid", "Delhi", "Rome"}, 2),
            new Question("What is 5 + 10?", new String[] {"10", "11", "15", "13"}, 2),
            new Question("Who wrote 'To Kill a Mockingbird'?", new String[] {"Harper Lee", "J.K. Rowling", "Ernest Hemingway", "Mark Twain"}, 0),
            new Question("Which planet is known as the Red Planet?", new String[] {"Earth", "Mars", "Jupiter", "Venus"}, 1),
            new Question("What is the largest ocean on Earth?", new String[] {"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 3)
        };

        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
