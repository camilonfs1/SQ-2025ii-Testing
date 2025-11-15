class InMemoryUserRepo:
    def __init__(self):
        self.users = {}

    def add_user(self, user_id, user_data):
        #get user metadata
        #calculate user preferences
        self.users[user_id] = user_data

    def get_user(self, user_id):
        return self.users.get(user_id)

    def delete_user(self, user_id):
        if user_id in self.users:
            del self.users[user_id]

repo = InMemoryUserRepo()
repo.add_user("1", {"name": "Alice"})
repo.delete_user("<EMAIL>")
repo.add_user("1", {"name": "Alice"})