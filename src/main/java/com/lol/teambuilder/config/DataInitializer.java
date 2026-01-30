package com.lol.teambuilder.config;

import com.lol.teambuilder.model.Champion;
import com.lol.teambuilder.repository.ChampionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final ChampionRepository championRepository;
    
    @Override
    public void run(String... args) {
        // Top Lane
        championRepository.save(new Champion("Darius", "Lutador", "fighter", "🗡️", 9, 1, 7, 6, "top"));
        championRepository.save(new Champion("Garen", "Lutador", "fighter", "⚔️", 8, 0, 8, 4, "top"));
        championRepository.save(new Champion("Fiora", "Lutador", "fighter", "🤺", 9, 0, 5, 2, "top"));
        championRepository.save(new Champion("Camille", "Lutador", "fighter", "🦾", 8, 1, 6, 6, "top"));
        championRepository.save(new Champion("Sett", "Lutador", "fighter", "🥊", 8, 0, 8, 7, "top"));
        championRepository.save(new Champion("Ornn", "Tank", "tank", "🔨", 5, 4, 10, 9, "top"));
        championRepository.save(new Champion("Malphite", "Tank", "tank", "🗿", 3, 6, 10, 8, "top"));
        championRepository.save(new Champion("Teemo", "Atirador", "marksman", "🍄", 5, 8, 2, 4, "top"));
        championRepository.save(new Champion("Mordekaiser", "Lutador", "fighter", "👻", 0, 10, 7, 6, "top"));
        championRepository.save(new Champion("Shen", "Tank", "tank", "🛡️", 4, 4, 9, 7, "top"));
        
        // Jungle
        championRepository.save(new Champion("Lee Sin", "Lutador", "fighter", "🐉", 9, 0, 5, 6, "jungle"));
        championRepository.save(new Champion("Elise", "Mago", "mage", "🕷️", 2, 9, 4, 7, "jungle"));
        championRepository.save(new Champion("Kayn", "Assassino", "assassin", "👹", 9, 1, 6, 5, "jungle"));
        championRepository.save(new Champion("Graves", "Atirador", "marksman", "🔫", 10, 0, 6, 3, "jungle"));
        championRepository.save(new Champion("Kha'Zix", "Assassino", "assassin", "🦗", 10, 0, 3, 3, "jungle"));
        championRepository.save(new Champion("Amumu", "Tank", "tank", "🧟", 2, 7, 9, 10, "jungle"));
        championRepository.save(new Champion("Sejuani", "Tank", "tank", "🐗", 3, 5, 10, 10, "jungle"));
        championRepository.save(new Champion("Evelynn", "Assassino", "assassin", "😈", 1, 10, 2, 6, "jungle"));
        championRepository.save(new Champion("Warwick", "Lutador", "fighter", "🐺", 8, 2, 7, 7, "jungle"));
        championRepository.save(new Champion("Hecarim", "Lutador", "fighter", "🐴", 7, 2, 6, 7, "jungle"));
        
        // Mid Lane
        championRepository.save(new Champion("Ahri", "Mago", "mage", "🦊", 1, 9, 3, 6, "mid"));
        championRepository.save(new Champion("Zed", "Assassino", "assassin", "🥷", 10, 0, 3, 3, "mid"));
        championRepository.save(new Champion("Yasuo", "Lutador", "fighter", "🌪️", 10, 0, 4, 7, "mid"));
        championRepository.save(new Champion("Lux", "Mago", "mage", "✨", 0, 10, 2, 8, "mid"));
        championRepository.save(new Champion("Syndra", "Mago", "mage", "🔮", 0, 10, 2, 7, "mid"));
        championRepository.save(new Champion("Katarina", "Assassino", "assassin", "🗡️", 5, 9, 2, 2, "mid"));
        championRepository.save(new Champion("Orianna", "Mago", "mage", "⚽", 0, 9, 3, 8, "mid"));
        championRepository.save(new Champion("Fizz", "Assassino", "assassin", "🐟", 2, 10, 3, 6, "mid"));
        championRepository.save(new Champion("Viktor", "Mago", "mage", "⚡", 0, 10, 2, 6, "mid"));
        championRepository.save(new Champion("Veigar", "Mago", "mage", "🎩", 0, 10, 2, 8, "mid"));
        
        // ADC
        championRepository.save(new Champion("Jinx", "Atirador", "marksman", "🔫", 10, 0, 2, 5, "adc"));
        championRepository.save(new Champion("Caitlyn", "Atirador", "marksman", "🎯", 10, 0, 2, 4, "adc"));
        championRepository.save(new Champion("Vayne", "Atirador", "marksman", "🏹", 10, 1, 2, 5, "adc"));
        championRepository.save(new Champion("Ezreal", "Atirador", "marksman", "💫", 7, 5, 3, 3, "adc"));
        championRepository.save(new Champion("Kai'Sa", "Atirador", "marksman", "🦋", 8, 5, 3, 3, "adc"));
        championRepository.save(new Champion("Ashe", "Atirador", "marksman", "❄️", 9, 0, 2, 8, "adc"));
        championRepository.save(new Champion("Jhin", "Atirador", "marksman", "🎭", 10, 0, 2, 7, "adc"));
        championRepository.save(new Champion("Miss Fortune", "Atirador", "marksman", "💰", 9, 1, 2, 4, "adc"));
        championRepository.save(new Champion("Draven", "Atirador", "marksman", "🪓", 10, 0, 2, 5, "adc"));
        championRepository.save(new Champion("Twitch", "Atirador", "marksman", "🐀", 10, 1, 2, 3, "adc"));
        
        // Support
        championRepository.save(new Champion("Thresh", "Suporte", "support", "⛓️", 3, 4, 7, 10, "support"));
        championRepository.save(new Champion("Leona", "Tank", "tank", "☀️", 4, 4, 9, 10, "support"));
        championRepository.save(new Champion("Lulu", "Suporte", "support", "🧚", 0, 5, 3, 8, "support"));
        championRepository.save(new Champion("Morgana", "Mago", "mage", "🌙", 0, 7, 4, 9, "support"));
        championRepository.save(new Champion("Blitzcrank", "Tank", "tank", "🤖", 5, 4, 8, 9, "support"));
        championRepository.save(new Champion("Soraka", "Suporte", "support", "🦄", 0, 4, 2, 6, "support"));
        championRepository.save(new Champion("Pyke", "Assassino", "assassin", "🗡️", 9, 0, 4, 8, "support"));
        championRepository.save(new Champion("Nautilus", "Tank", "tank", "⚓", 4, 5, 10, 10, "support"));
        championRepository.save(new Champion("Janna", "Suporte", "support", "🌬️", 0, 4, 2, 9, "support"));
        championRepository.save(new Champion("Senna", "Atirador", "marksman", "👻", 8, 1, 3, 6, "support"));
    }
}
