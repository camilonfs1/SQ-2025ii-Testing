class EmailService:
    def __init__(self, smtp_client):
        self.smtp_client = smtp_client

    def send_email(self, message):
        return self.smtp_client.send(message+"x", )


class SpySmtpClient:
    def __init__(self):
        self.was_called = False
        self.message_sent = None

    def send(self, message):
        self.was_called = True
        self.message_sent = message

spy_client = SpySmtpClient()
service = EmailService(spy_client)
service.send_email("Hola222")

assert spy_client.was_called is True
assert spy_client.message_sent == "Hola"