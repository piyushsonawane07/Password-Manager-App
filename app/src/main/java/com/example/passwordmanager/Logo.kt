package com.example.passwordmanager



import android.widget.ImageView


class Logo {

    // For setting the logo in password item in recyclerView
    fun setLogo(holder:PasswordsAdapter.ViewHolder,title:String){

        if (title.contains("facebook")|| title.contains("Facebook")){
            holder.imageView.setBackgroundResource(R.drawable.ic_facebook)
        }
        else if (title.contains("instagram")|| title.contains("Instagram")){
            holder.imageView.setBackgroundResource(R.drawable.ic_instagram)
        }
        else if(title.contains("twitter")|| title.contains("Twitter")){
            holder.imageView.setBackgroundResource(R.drawable.ic_twitter)
        }
        else if (title.contains("google")|| title.contains("Google")){
            holder.imageView.setBackgroundResource(R.drawable.ic_google_logo)
        }
        else if (title.contains("apple")|| title.contains("Apple")){
            holder.imageView.setBackgroundResource(R.drawable.ic_apple)
        }
        else if (title.contains("netflix")|| title.contains("Netflix")){
            holder.imageView.setBackgroundResource(R.drawable.ic_netflix)
        }
        else if (title.contains("youtube")|| title.contains("Youtube") || title.contains("youTube")|| title.contains("YouTube")){
            holder.imageView.setBackgroundResource(R.drawable.ic_youtube)
        }
        else if (title.contains("whatsapp")|| title.contains("Whatsapp")){
            holder.imageView.setBackgroundResource(R.drawable.ic_whatsapp)
        }
        else if (title.contains("spotify")|| title.contains("Spotify")){
            holder.imageView.setBackgroundResource(R.drawable.ic_spotify)
        }
        else if (title.contains("telegram")|| title.contains("Telegram")){
            holder.imageView.setBackgroundResource(R.drawable.ic_telegram)
        }
        else if (title.contains("github")|| title.contains("Github")){
            holder.imageView.setBackgroundResource(R.drawable.ic_github)
        }
        else if (title.contains("medium")|| title.contains("Medium")){
            holder.imageView.setBackgroundResource(R.drawable.ic_medium)
        }
        else if (title.contains("dribble")|| title.contains("Dribble")){
            holder.imageView.setBackgroundResource(R.drawable.ic_dribbble)
        }
        else if (title.contains("behance")|| title.contains("Behance")){
            holder.imageView.setBackgroundResource(R.drawable.ic_behance)
        }
        else if (title.contains("pinterest")|| title.contains("Pinterest")){
            holder.imageView.setBackgroundResource(R.drawable.ic_pinterest)
        }
        else if (title.contains("reddit")|| title.contains("Reddit")){
            holder.imageView.setBackgroundResource(R.drawable.ic_reddit)
        }
        else if (title.contains("slack")|| title.contains("Slack")){
            holder.imageView.setBackgroundResource(R.drawable.ic_slack)
        }
        else if (title.contains("teams")|| title.contains("Teams")){
            holder.imageView.setBackgroundResource(R.drawable.ic_teams)
        }
        else if (title.contains("gmail")|| title.contains("Gmail")){
            holder.imageView.setBackgroundResource(R.drawable.ic_gmail)
        }
        else if (title.contains("amazon")|| title.contains("Amazon")){
            holder.imageView.setBackgroundResource(R.drawable.ic_amazon)
        }
        else if (title.contains("zeplin")|| title.contains("Zeplin")){
            holder.imageView.setBackgroundResource(R.drawable.ic_zeplin)
        }
        else if (title.contains("discord")|| title.contains("Discord")){
            holder.imageView.setBackgroundResource(R.drawable.ic_discord)
        }
        else if (title.contains("stackoverflow")|| title.contains("Stackoverflow")){
            holder.imageView.setBackgroundResource(R.drawable.ic_stack_overflow)
        }
        else if (title.contains("quora")|| title.contains("Quora")){
            holder.imageView.setBackgroundResource(R.drawable.ic_quora)
        }
        else if (title.contains("trello")|| title.contains("Trello")){
            holder.imageView.setBackgroundResource(R.drawable.ic_trello)
        }
        else if (title.contains("notion")|| title.contains("Notion")){
            holder.imageView.setBackgroundResource(R.drawable.ic_notion)
        }
        else if (title.contains("figma")|| title.contains("Figma")){
            holder.imageView.setBackgroundResource(R.drawable.ic_figma)
        }
        else{
            holder.imageView.setBackgroundResource(R.drawable.ic_key)
        }
    }



    //for setting the logo in viewPassword
    fun setViewLogo(imageView:ImageView,title: String){
        if (title.contains("facebook")|| title.contains("Facebook")){
            imageView.setBackgroundResource(R.drawable.ic_facebook)
        }
        else if (title.contains("instagram")|| title.contains("Instagram")){
            imageView.setBackgroundResource(R.drawable.ic_instagram)
        }
        else if(title.contains("twitter")|| title.contains("Twitter")){
            imageView.setBackgroundResource(R.drawable.ic_twitter)
        }
        else if (title.contains("google")|| title.contains("Google")){
            imageView.setBackgroundResource(R.drawable.ic_google_logo)
        }
        else if (title.contains("apple")|| title.contains("Apple")){
            imageView.setBackgroundResource(R.drawable.ic_apple)
        }
        else if (title.contains("netflix")|| title.contains("Netflix")){
            imageView.setBackgroundResource(R.drawable.ic_netflix)
        }
        else if (title.contains("youtube")|| title.contains("Youtube") || title.contains("youTube")|| title.contains("YouTube")){
            imageView.setBackgroundResource(R.drawable.ic_youtube)
        }
        else if (title.contains("whatsapp")|| title.contains("Whatsapp")){
            imageView.setBackgroundResource(R.drawable.ic_whatsapp)
        }
        else if (title.contains("spotify")|| title.contains("Spotify")){
            imageView.setBackgroundResource(R.drawable.ic_spotify)
        }
        else if (title.contains("telegram")|| title.contains("Telegram")){
            imageView.setBackgroundResource(R.drawable.ic_telegram)
        }
        else if (title.contains("github")|| title.contains("Github")){
            imageView.setBackgroundResource(R.drawable.ic_github)
        }
        else if (title.contains("medium")|| title.contains("Medium")){
            imageView.setBackgroundResource(R.drawable.ic_medium)
        }
        else if (title.contains("dribble")|| title.contains("Dribble")){
            imageView.setBackgroundResource(R.drawable.ic_dribbble)
        }
        else if (title.contains("behance")|| title.contains("Behance")){
            imageView.setBackgroundResource(R.drawable.ic_behance)
        }
        else if (title.contains("pinterest")|| title.contains("Pinterest")){
            imageView.setBackgroundResource(R.drawable.ic_pinterest)
        }
        else if (title.contains("reddit")|| title.contains("Reddit")){
            imageView.setBackgroundResource(R.drawable.ic_reddit)
        }
        else if (title.contains("slack")|| title.contains("Slack")){
            imageView.setBackgroundResource(R.drawable.ic_slack)
        }
        else if (title.contains("teams")|| title.contains("Teams")){
            imageView.setBackgroundResource(R.drawable.ic_teams)
        }
        else if (title.contains("gmail")|| title.contains("Gmail")){
            imageView.setBackgroundResource(R.drawable.ic_gmail)
        }
        else if (title.contains("amazon")|| title.contains("Amazon")){
            imageView.setBackgroundResource(R.drawable.ic_amazon)
        }
        else if (title.contains("zeplin")|| title.contains("Zeplin")){
            imageView.setBackgroundResource(R.drawable.ic_zeplin)
        }
        else if (title.contains("discord")|| title.contains("Discord")){
            imageView.setBackgroundResource(R.drawable.ic_discord)
        }
        else if (title.contains("stackoverflow")|| title.contains("Stackoverflow")){
            imageView.setBackgroundResource(R.drawable.ic_stack_overflow)
        }
        else if (title.contains("quora")|| title.contains("Quora")){
            imageView.setBackgroundResource(R.drawable.ic_quora)
        }
        else if (title.contains("trello")|| title.contains("Trello")){
            imageView.setBackgroundResource(R.drawable.ic_trello)
        }
        else if (title.contains("notion")|| title.contains("Notion")){
            imageView.setBackgroundResource(R.drawable.ic_notion)
        }
        else if (title.contains("figma")|| title.contains("Figma")){
            imageView.setBackgroundResource(R.drawable.ic_figma)
        }
        else{
            imageView.setBackgroundResource(R.drawable.ic_key)
        }

    }
}