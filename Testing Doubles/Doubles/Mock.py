class EmailService:
    def __init__(self, smtp_client):
        self.smtp_client = smtp_client

    def send_email(self, message, subject, recipient):
        return self.smtp_client.send(message, subject, recipient)


class MockSmtpClient:
    def __init__(self):
        self.expected_message = None
        self.expected_subject = None
        self.expected_recipient = None
        self.was_called = False

    def expect_send(self, expected_message, expected_subject, expected_recipient):
        self.expected_message = expected_message
        self.expected_subject = expected_subject
        self.expected_recipient = expected_recipient

    def send(self, message, subject, recipient):
        self.was_called = True
        assert message == self.expected_message, "Mensaje inesperado en mock"
        assert subject == self.expected_subject, "Asunto inesperado en mock"
        assert recipient == self.expected_recipient, "Destinatario inesperado en mock"

mock_client = MockSmtpClient()
mock_client.expect_send("Hola", "Saludo", "test@example.com")

service = EmailService(mock_client)
service.smtp_client.send("Hola", "Saludo", "test@example.com")  # pasa
# Si alguno de los parámetros no coincide, lanza AssertionError