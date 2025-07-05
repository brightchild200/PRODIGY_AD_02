# ✅ ToDoList App

A simple and clean **Android Kotlin** Todo app using **Firestore** to manage tasks, with separate views for **Incomplete** and **Completed** tasks. Users can add, edit and delete the tasks along with categorizing the tasks as completed and pending.

I have designed the **whole UI**, from Background to all color scheme and all !!! I hope you like it ...

---

## UI Design

![App Demo](https://github.com/brightchild200/PRODIGY_AD_02/blob/205082bd33d83403e9f68ef1c2aebaa4b1d19d7d/app/src/main/res/drawable/task_list.png)
![](https://github.com/brightchild200/PRODIGY_AD_02/blob/205082bd33d83403e9f68ef1c2aebaa4b1d19d7d/app/src/main/res/drawable/completed_task.png)
---

## 📽️ Demo Video

[Watch the demo video](https://github.com/user-attachments/assets/f3023a53-46b0-41ed-a14b-25e434cf9144)

## 🛠️ Features

* 📋 View all **incomplete tasks** on the main screen
* ✅ Tap a checkbox to mark as **completed** (moves to Completed screen)
* ✏️ **Edit**, **Delete**, **Add** tasks via TaskActivity
* ✅ ✅ View completed tasks and tap their checkbox to move them back

---

## 🔧 Screens

1. **MainActivity** – Displays incomplete tasks with options to complete, edit, delete, and add new.
2. **TaskActivity** – Add a new task or edit/delete an existing one.
3. **CompletedTaskActivity** – Displays completed tasks with option to restore to incomplete.

---

## ☁️ Firebase Integration

Firestore is used to store tasks with this schema:

```json
{
  "description": "String",
  "completed": Boolean
}
```

2. Open Android Studio, sync Gradle, and run the app.

---

## 💡 How It Works

* **MainActivity**:

  * Observes Firestore for documents with `completed=false`
  * Shows tasks in a vertical list
  * Checkbox updates to Firestore when marking completed
  * Buttons navigate to TaskActivity

* **TaskActivity**:

  * In *add mode*, only “Add” is enabled
  * In *edit/delete mode*, pre-fills data and enables “Edit” and “Delete”
  * On any button action, writes to Firestore and returns to MainActivity

* **CompletedTaskActivity**:

  * Observes Firestore for `completed=true`
  * Checkbox toggles back to incomplete

---

## 🚀 Next Steps

* Add due dates or reminders
* Add Calendar and mark the task in calendar
* Add soft music when the checkbox is checked or unchecked
* Polish UI with Material Design components

---

## 🤝 Contributing

Improvements are welcome! Feel free to submit a PR or open an issue.
