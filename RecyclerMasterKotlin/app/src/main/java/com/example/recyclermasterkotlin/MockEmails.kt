package com.example.recyclermasterkotlin

class MockEmails {

    companion object {
        fun fakeEmails(): MutableList<Email> {
            return arrayListOf(
                Email(
                    "Facebook", "Veja nossas três dicas principais para aumentar as suas vendas",
                    "Olá Tiago, Você precisa ver esse site para", "5 jun"
                ),
                Email(
                    "Facebook", "Um amigo quer que você curta uma Página dele",
                    "Fulano convidou você para curtir a sua Página no Faceboo", "30 mai"
                ),
                Email(
                    "Youtube", "Tiago Aguiar acabou de enviar um vídeo",
                    "Tiago Aguiar enviou ANDROID: GOOGLE MAPS, LOCATION", "30 mai",
                    stared = true, unread = true
                ),
                Email(
                    "Instagram", "tiagoaguiar.oficial começou a seguir-te",
                    "tiagoaguiar.oficial, tens um novo seguidor", "18 mai"
                ),
                Email(
                    "Youtube", "Veja nossas três dicas principais para aumentar as suas vendas",
                    "Olá Tiago, Você precisa ver esse site para", "5 jun"
                ),
                Email(
                    "Facebook", "Veja nossas três dicas principais para aumentar as suas vendas",
                    "Olá Tiago, Você precisa ver esse site para", "5 jun"
                ),
                Email(
                    "Facebook", "Um amigo quer que você curta uma Página dele",
                    "Fulano convidou você para curtir a sua Página no Faceboo", "30 mai"
                ),
                Email(
                    "Youtube", "Tiago Aguiar acabou de enviar um vídeo",
                    "Tiago Aguiar enviou ANDROID: GOOGLE MAPS, LOCATION", "30 mai",
                    stared = true, unread = true
                ),
                Email(
                    "Instagram", "tiagoaguiar.oficial começou a seguir-te",
                    "tiagoaguiar.oficial, tens um novo seguidor", "18 mai"
                ),
                Email(
                    "Youtube", "Veja nossas três dicas principais para aumentar as suas vendas",
                    "Olá Tiago, Você precisa ver esse site para", "5 jun"
                ),
            )
        }
    }
}