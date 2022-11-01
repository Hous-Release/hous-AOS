package hous.release.android.presentation.todo.add

import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.entity.response.ToDoUser

abstract class ToDoUiState {
    abstract val selectedUsers: List<ToDoUser>
    abstract val todoUsers: List<ToDoUser>
    abstract val isPushNotification: Boolean
    abstract val buttonState: ButtonState
    abstract val isBlankTodoName: Boolean
}
