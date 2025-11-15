class StubSmtpClient:
    def send(self, message):
        return False  # siempre simula éxito

class EmailService:
    def __init__(self, smtp_client):
        self.smtp_client = smtp_client

    def send_email(self, message):
        return self.smtp_client.send(message)

# Uso en test
stub_client = StubSmtpClient()
service = EmailService(stub_client)
assert service.send_email("Hola") is True