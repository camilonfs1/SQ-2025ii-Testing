# Un Dummy es un objeto que se pasa a una función o clase pero nunca se utiliza.

class DummySmtpClient:
    pass


class EmailService:
    def __init__(self, smtp_client):
        self.smtp_client = smtp_client

    def notify(self, message):
        print("Notificando:", message)


# Test:
dummy_client = DummySmtpClient()  # No hace nada
service = EmailService(dummy_client)
service.notify("Hola Mundo")  # Solo usa su propia lógica, ignora smtp_client