use std::io;

fn main() {
    println!("Guess the number!");
    println!("please input your guess.");
    let mut guess = String::new();
    io::stdin()
        .read_line(&mut guess)
        .expect("Fail to read line");

    println!("You guessed {}", guess);
}
