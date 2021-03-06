package no.runsafe.PlayerNotes.command;

import no.runsafe.PlayerNotes.NoteManager;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.AsyncCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.text.ChatColour;

public class SetCommand extends AsyncCommand
{
	public SetCommand(NoteManager manager, IScheduler scheduler)
	{
		super(
			"set", "Sets a note for the given player", "runsafe.note.set.<tier>", scheduler,
			new TierArgument(manager), new TrailingArgument("note")
		);
		this.manager = manager;
	}

	@Override
	public String OnAsyncExecute(ICommandExecutor executor, IArgumentList params)
	{
		IPlayer target = params.getValue("player");
		if (target == null)
			return null;
		String note = params.get("note");

		if (!executor.hasPermission("runsafe.note.colour"))
			note = ChatColour.Strip(note);

		IPlayer setter = null;
		if (executor instanceof IPlayer)
			setter = (IPlayer) executor;

		manager.setNoteForPlayer(setter, target, params.get("tier"), note);
		return String.format(("%s note set for %s."), params.get("tier"), target.getPrettyName());
	}

	private final NoteManager manager;
}
