// Base de dados de campeões
const champions = [
    // Top Lane
    { name: "Darius", role: "Lutador", class: "fighter", icon: "🗡️", damage: { physical: 9, magic: 1 }, tank: 7, cc: 6, lanes: ["top"] },
    { name: "Garen", role: "Lutador", class: "fighter", icon: "⚔️", damage: { physical: 8, magic: 0 }, tank: 8, cc: 4, lanes: ["top"] },
    { name: "Fiora", role: "Lutador", class: "fighter", icon: "🤺", damage: { physical: 9, magic: 0 }, tank: 5, cc: 2, lanes: ["top"] },
    { name: "Camille", role: "Lutador", class: "fighter", icon: "🦾", damage: { physical: 8, magic: 1 }, tank: 6, cc: 6, lanes: ["top"] },
    { name: "Sett", role: "Lutador", class: "fighter", icon: "🥊", damage: { physical: 8, magic: 0 }, tank: 8, cc: 7, lanes: ["top"] },
    { name: "Ornn", role: "Tank", class: "tank", icon: "🔨", damage: { physical: 5, magic: 4 }, tank: 10, cc: 9, lanes: ["top"] },
    { name: "Malphite", role: "Tank", class: "tank", icon: "🗿", damage: { physical: 3, magic: 6 }, tank: 10, cc: 8, lanes: ["top"] },
    { name: "Teemo", role: "Atirador", class: "marksman", icon: "🍄", damage: { physical: 5, magic: 8 }, tank: 2, cc: 4, lanes: ["top"] },
    { name: "Mordekaiser", role: "Lutador", class: "fighter", icon: "👻", damage: { physical: 0, magic: 10 }, tank: 7, cc: 6, lanes: ["top"] },
    { name: "Shen", role: "Tank", class: "tank", icon: "🛡️", damage: { physical: 4, magic: 4 }, tank: 9, cc: 7, lanes: ["top"] },

    // Jungle
    { name: "Lee Sin", role: "Lutador", class: "fighter", icon: "🐉", damage: { physical: 9, magic: 0 }, tank: 5, cc: 6, lanes: ["jungle"] },
    { name: "Elise", role: "Mago", class: "mage", icon: "🕷️", damage: { physical: 2, magic: 9 }, tank: 4, cc: 7, lanes: ["jungle"] },
    { name: "Kayn", role: "Assassino", class: "assassin", icon: "👹", damage: { physical: 9, magic: 1 }, tank: 6, cc: 5, lanes: ["jungle"] },
    { name: "Graves", role: "Atirador", class: "marksman", icon: "🔫", damage: { physical: 10, magic: 0 }, tank: 6, cc: 3, lanes: ["jungle"] },
    { name: "Kha'Zix", role: "Assassino", class: "assassin", icon: "🦗", damage: { physical: 10, magic: 0 }, tank: 3, cc: 3, lanes: ["jungle"] },
    { name: "Amumu", role: "Tank", class: "tank", icon: "🧟", damage: { physical: 2, magic: 7 }, tank: 9, cc: 10, lanes: ["jungle"] },
    { name: "Sejuani", role: "Tank", class: "tank", icon: "🐗", damage: { physical: 3, magic: 5 }, tank: 10, cc: 10, lanes: ["jungle"] },
    { name: "Evelynn", role: "Assassino", class: "assassin", icon: "😈", damage: { physical: 1, magic: 10 }, tank: 2, cc: 6, lanes: ["jungle"] },
    { name: "Warwick", role: "Lutador", class: "fighter", icon: "🐺", damage: { physical: 8, magic: 2 }, tank: 7, cc: 7, lanes: ["jungle"] },
    { name: "Hecarim", role: "Lutador", class: "fighter", icon: "🐴", damage: { physical: 7, magic: 2 }, tank: 6, cc: 7, lanes: ["jungle"] },

    // Mid Lane
    { name: "Ahri", role: "Mago", class: "mage", icon: "🦊", damage: { physical: 1, magic: 9 }, tank: 3, cc: 6, lanes: ["mid"] },
    { name: "Zed", role: "Assassino", class: "assassin", icon: "🥷", damage: { physical: 10, magic: 0 }, tank: 3, cc: 3, lanes: ["mid"] },
    { name: "Yasuo", role: "Lutador", class: "fighter", icon: "🌪️", damage: { physical: 10, magic: 0 }, tank: 4, cc: 7, lanes: ["mid"] },
    { name: "Lux", role: "Mago", class: "mage", icon: "✨", damage: { physical: 0, magic: 10 }, tank: 2, cc: 8, lanes: ["mid"] },
    { name: "Syndra", role: "Mago", class: "mage", icon: "🔮", damage: { physical: 0, magic: 10 }, tank: 2, cc: 7, lanes: ["mid"] },
    { name: "Katarina", role: "Assassino", class: "assassin", icon: "🗡️", damage: { physical: 5, magic: 9 }, tank: 2, cc: 2, lanes: ["mid"] },
    { name: "Orianna", role: "Mago", class: "mage", icon: "⚽", damage: { physical: 0, magic: 9 }, tank: 3, cc: 8, lanes: ["mid"] },
    { name: "Fizz", role: "Assassino", class: "assassin", icon: "🐟", damage: { physical: 2, magic: 10 }, tank: 3, cc: 6, lanes: ["mid"] },
    { name: "Viktor", role: "Mago", class: "mage", icon: "⚡", damage: { physical: 0, magic: 10 }, tank: 2, cc: 6, lanes: ["mid"] },
    { name: "Veigar", role: "Mago", class: "mage", icon: "🎩", damage: { physical: 0, magic: 10 }, tank: 2, cc: 8, lanes: ["mid"] },

    // ADC
    { name: "Jinx", role: "Atirador", class: "marksman", icon: "🔫", damage: { physical: 10, magic: 0 }, tank: 2, cc: 5, lanes: ["adc"] },
    { name: "Caitlyn", role: "Atirador", class: "marksman", icon: "🎯", damage: { physical: 10, magic: 0 }, tank: 2, cc: 4, lanes: ["adc"] },
    { name: "Vayne", role: "Atirador", class: "marksman", icon: "🏹", damage: { physical: 10, magic: 1 }, tank: 2, cc: 5, lanes: ["adc"] },
    { name: "Ezreal", role: "Atirador", class: "marksman", icon: "💫", damage: { physical: 7, magic: 5 }, tank: 3, cc: 3, lanes: ["adc"] },
    { name: "Kai'Sa", role: "Atirador", class: "marksman", icon: "🦋", damage: { physical: 8, magic: 5 }, tank: 3, cc: 3, lanes: ["adc"] },
    { name: "Ashe", role: "Atirador", class: "marksman", icon: "❄️", damage: { physical: 9, magic: 0 }, tank: 2, cc: 8, lanes: ["adc"] },
    { name: "Jhin", role: "Atirador", class: "marksman", icon: "🎭", damage: { physical: 10, magic: 0 }, tank: 2, cc: 7, lanes: ["adc"] },
    { name: "Miss Fortune", role: "Atirador", class: "marksman", icon: "💰", damage: { physical: 9, magic: 1 }, tank: 2, cc: 4, lanes: ["adc"] },
    { name: "Draven", role: "Atirador", class: "marksman", icon: "🪓", damage: { physical: 10, magic: 0 }, tank: 2, cc: 5, lanes: ["adc"] },
    { name: "Twitch", role: "Atirador", class: "marksman", icon: "🐀", damage: { physical: 10, magic: 1 }, tank: 2, cc: 3, lanes: ["adc"] },

    // Support
    { name: "Thresh", role: "Suporte", class: "support", icon: "⛓️", damage: { physical: 3, magic: 4 }, tank: 7, cc: 10, lanes: ["support"] },
    { name: "Leona", role: "Tank", class: "tank", icon: "☀️", damage: { physical: 4, magic: 4 }, tank: 9, cc: 10, lanes: ["support"] },
    { name: "Lulu", role: "Suporte", class: "support", icon: "🧚", damage: { physical: 0, magic: 5 }, tank: 3, cc: 8, lanes: ["support"] },
    { name: "Morgana", role: "Mago", class: "mage", icon: "🌙", damage: { physical: 0, magic: 7 }, tank: 4, cc: 9, lanes: ["support"] },
    { name: "Blitzcrank", role: "Tank", class: "tank", icon: "🤖", damage: { physical: 5, magic: 4 }, tank: 8, cc: 9, lanes: ["support"] },
    { name: "Soraka", role: "Suporte", class: "support", icon: "🦄", damage: { physical: 0, magic: 4 }, tank: 2, cc: 6, lanes: ["support"] },
    { name: "Pyke", role: "Assassino", class: "assassin", icon: "🗡️", damage: { physical: 9, magic: 0 }, tank: 4, cc: 8, lanes: ["support"] },
    { name: "Nautilus", role: "Tank", class: "tank", icon: "⚓", damage: { physical: 4, magic: 5 }, tank: 10, cc: 10, lanes: ["support"] },
    { name: "Janna", role: "Suporte", class: "support", icon: "🌬️", damage: { physical: 0, magic: 4 }, tank: 2, cc: 9, lanes: ["support"] },
    { name: "Senna", role: "Atirador", class: "marksman", icon: "👻", damage: { physical: 8, magic: 1 }, tank: 3, cc: 6, lanes: ["support"] }
];
